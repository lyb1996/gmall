package com.steven.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.steven.gmall.bean.PmsSkuAttrValue;
import com.steven.gmall.bean.PmsSkuImage;
import com.steven.gmall.bean.PmsSkuInfo;
import com.steven.gmall.bean.PmsSkuSaleAttrValue;
import com.steven.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.steven.gmall.manage.mapper.PmsSkuImageMapper;
import com.steven.gmall.manage.mapper.PmsSkuInfoMapper;
import com.steven.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.steven.gmall.service.SkuService;
import com.steven.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);

            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);

            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);

            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId, String ip) {
        System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "进入商品详情的请求");
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        // 链接redis缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询redis缓存
        String skukey = "sku:" + skuId + ":info";
        String skuJson = jedis.get(skukey);
        if (StringUtils.isNoneBlank(skuJson)) { //if(skuJson!=null&&!skuJson.equals(""))
            System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "从缓存中获取商品详情");
            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        } else {
            // 如果缓存中没有，查询mysql
            System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "发现缓存中没有，申请分布式锁" + "sku:" + skuId + ":lock");
            // 设置分布式锁
            String token = UUID.randomUUID().toString(); // 设置一个token值，用于分布式锁的value
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10 * 1000); //拿到锁的线程有10秒的过期时间
            if (StringUtils.isNoneBlank(OK) && OK.equals("OK")) {
                // 设置成功，有权在10秒内访问数据库
                System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "有权在10秒内访问mysql数据库");
                pmsSkuInfo = getSkuByIdFromDb(skuId);
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pmsSkuInfo != null) {
                    // 将mysql查询结果存入redis
                    jedis.set("sku:" + skuId + ":info", JSON.toJSONString(pmsSkuInfo));
                } else {
                    // mysql中找不到不到该sku
                    // 为了防止缓存穿透，将null或空字符串设置给redis
                    jedis.setex("sku:" + skuId + ":info", 60 * 3, JSON.toJSONString(""));
                }
                // 在访问mysql后，将分布式锁释放
                System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "使用完毕，将锁释放");
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if (StringUtils.isNoneBlank(lockToken) && lockToken.equals(token)) {
                    //jedis.eval("lua");可与用lua脚本，在查询到key的同时删除该key，防止高并发下的意外的发生
                    jedis.del("sku:" + skuId + ":lock"); // 用token确认删的是自己的锁
                }
            } else {
                // 设置失败，进入自旋（该线程睡眠几秒后，重新尝试访问本方法）
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ip为" + ip + "的同学" + Thread.currentThread().getName() + "没有拿到锁，开始自旋");
                return getSkuById(skuId, ip);
            }
        }
        // 关闭redis缓存
        jedis.close();
        return pmsSkuInfo;
    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId) {
        // 获得skus商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        // 获得sku图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> skuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(skuImages);

        return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }
}

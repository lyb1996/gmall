package com.steven.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.steven.gmall.bean.PmsSkuAttrValue;
import com.steven.gmall.bean.PmsSkuImage;
import com.steven.gmall.bean.PmsSkuInfo;
import com.steven.gmall.bean.PmsSkuSaleAttrValue;
import com.steven.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.steven.gmall.manage.mapper.PmsSkuImageMapper;
import com.steven.gmall.manage.mapper.PmsSkuInfoMapper;
import com.steven.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.steven.gmall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}

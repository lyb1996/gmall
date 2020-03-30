package com.steven.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.steven.gmall.bean.PmsBaseAttrInfo;
import com.steven.gmall.bean.PmsProductImage;
import com.steven.gmall.bean.PmsProductInfo;
import com.steven.gmall.bean.PmsProductSaleAttr;
import com.steven.gmall.manage.util.PmsUploadUtil;
import com.steven.gmall.service.Spuservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {

    @Reference
    Spuservice spuservice;

    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id) {
        List<PmsProductInfo> pmsProductInfos = spuservice.spuList(catalog3Id);
        return pmsProductInfos;
    }

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public void saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuservice.saveSpuInfo(pmsProductInfo);
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        //将图片或者音视频上传到分布式的文件存储系统
        //将图片的存储路径返回给页面
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(imgUrl);
        return imgUrl;
    }

    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        List<PmsProductSaleAttr> pmsBaseAttrInfos = spuservice.spuSaleAttrList(spuId);
        return pmsBaseAttrInfos;
    }

    @RequestMapping("spuImageList")
    @ResponseBody
    public List<PmsProductImage> spuImageList(String spuId) {
        List<PmsProductImage> pmsProductImages = spuservice.spuImageList(spuId);
        return pmsProductImages;
    }
}

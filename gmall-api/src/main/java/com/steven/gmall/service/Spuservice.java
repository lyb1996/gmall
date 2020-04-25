package com.steven.gmall.service;

import com.steven.gmall.bean.PmsProductImage;
import com.steven.gmall.bean.PmsProductInfo;
import com.steven.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface Spuservice {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId, String SkuId);
}

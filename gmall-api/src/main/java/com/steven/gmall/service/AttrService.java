package com.steven.gmall.service;

import com.steven.gmall.bean.PmsBaseAttrInfo;
import com.steven.gmall.bean.PmsBaseAttrValue;
import com.steven.gmall.bean.PmsBaseSaleAttr;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInforList(String catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}

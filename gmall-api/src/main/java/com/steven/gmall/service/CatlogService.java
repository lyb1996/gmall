package com.steven.gmall.service;

import com.steven.gmall.bean.PmsBaseCatalog1;
import com.steven.gmall.bean.PmsBaseCatalog2;
import com.steven.gmall.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatlogService {

    List<PmsBaseCatalog1> getCatalog1();

    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}

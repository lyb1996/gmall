package com.steven.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.steven.gmall.bean.PmsBaseCatalog1;
import com.steven.gmall.bean.PmsBaseCatalog2;
import com.steven.gmall.bean.PmsBaseCatalog3;
import com.steven.gmall.service.CatlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class CatlogController {
    @Reference
    CatlogService catlogService;

    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> catalog1s = catlogService.getCatalog1();
        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        List<PmsBaseCatalog2> catalog2s = catlogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        List<PmsBaseCatalog3> catalog3s = catlogService.getCatalog3(catalog2Id);
        return catalog3s;
    }

}

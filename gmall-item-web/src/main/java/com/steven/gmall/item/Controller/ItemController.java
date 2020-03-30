package com.steven.gmall.item.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId) {

        return "item";
    }

    @RequestMapping("index")
    public String index(ModelMap modelMap) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            list.add("循环数据"+i);
        }

        modelMap.put("list",list);
        modelMap.put("hello","hello thymeleaf!!");
        modelMap.put("check","0");
        return "index";
    }
}

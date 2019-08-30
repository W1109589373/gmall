package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.config.LoginRequire;
import com.atguigu.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class ItemController {

    @Reference
    private ManageService manageService;

    @LoginRequire
    @RequestMapping("{skuId}.html")
    public String skuInfoPage(@PathVariable String skuId, Model model){
        //基本信息 图片
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);
        model.addAttribute("skuInfo",skuInfo);
        //销售属性
       List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrListCheckBySku(skuInfo);
       model.addAttribute("spuSaleAttrList",spuSaleAttrList);

       //获取销售属性值拼接的集合
        List<SkuSaleAttrValue> skuSaleAttrValueList = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());

        String valueIdsKey="";
        Map<String, String> valuesSkuMap = new HashMap<>();

        if (skuSaleAttrValueList != null && skuSaleAttrValueList.size()>0){
            for (int i = 0; i < skuSaleAttrValueList.size(); i++) {
                SkuSaleAttrValue ssaV = skuSaleAttrValueList.get(i);
                //第一次 11  11/   11/12
                if (valueIdsKey != ""){
                    valueIdsKey += "|";
                }
                valueIdsKey += ssaV.getSaleAttrValueId();

                if((i+1)==skuSaleAttrValueList.size() || !ssaV.getSkuId().equals(skuSaleAttrValueList.get(i+1).getSkuId())){
                    valuesSkuMap.put(valueIdsKey,ssaV.getSkuId());
                    valueIdsKey="";
                }
            }
        }
        String valuesSkuJson  = JSON.toJSONString(valuesSkuMap);
        model.addAttribute("valuesSkuJson",valuesSkuJson);


        return "item";
    }


}

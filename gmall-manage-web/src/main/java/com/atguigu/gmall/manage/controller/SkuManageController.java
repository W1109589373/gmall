package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuLsInfo;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.ListService;
import com.atguigu.gmall.service.ManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@CrossOrigin
@RestController
public class SkuManageController {

    @Reference
    private ManageService manageService;

    @Reference
    private ListService listService;

    @RequestMapping("onSale")
    @ResponseBody
    public void onSale(String skuId){
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);

        SkuLsInfo skuLsInfo = new SkuLsInfo();
        // 属性拷贝
        try {
            BeanUtils.copyProperties(skuInfo,skuLsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listService.saveSkuInfo(skuLsInfo);
    }

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        return "ok";
    }

    @RequestMapping("spuSaleAttrList")
    public List<SpuSaleAttr> getspuSaleAttrList(String spuId){
        return manageService.getspuSaleAttrList(spuId);
    }

    @RequestMapping("spuImageList")
    public List<SpuImage> spuImageList(String spuId){
        return manageService.getSpuImageList(spuId);
    }


}

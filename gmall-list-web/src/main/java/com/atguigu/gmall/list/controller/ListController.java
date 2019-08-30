package com.atguigu.gmall.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.ListService;
import com.atguigu.gmall.service.ManageService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@CrossOrigin
public class ListController {

    @Reference
    private ListService listService;

    @Reference
    private ManageService manageService;

    @RequestMapping("list.html")
    public String getList(SkuLsParams skuLsParams, HttpServletRequest request){
        skuLsParams.setPageSize(2);
        SkuLsResult skuLsResult = listService.search(skuLsParams);
        //String strSearch = JSON.toJSONString(skuLsResult);
        //获取列表页面商品集合并放入域中
        List<SkuLsInfo> skuLsInfoList = skuLsResult.getSkuLsInfoList();
        request.setAttribute("skuLsInfoList",skuLsInfoList);

        //获取列表页面的平台属性及平台属性值
        List<String> attrValueIdList = skuLsResult.getAttrValueIdList();
        List<BaseAttrInfo> attrList =  manageService.getAttrList(attrValueIdList);
        request.setAttribute("attrList",attrList);

        // 已选的属性值列表
        List<BaseAttrValue> baseAttrValuesList = new ArrayList<>();

        // itco循环, 如果路径中的平台属性值valueId=查询出来的平台属性值,则把查询出来的平台属性值从它的集合中移除,把集合剩下的元素渲染到页面
        for (Iterator<BaseAttrInfo> iterator = attrList.iterator(); iterator.hasNext(); ) {
            BaseAttrInfo baseAttrInfo =  iterator.next();
            List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
            for (BaseAttrValue baseAttrValue : attrValueList) {
                if(skuLsParams.getValueId()!=null&&skuLsParams.getValueId().length>0){
                    for (String valueId : skuLsParams.getValueId()) {
                        //选中的属性值 和 查询结果的属性值
                        if(valueId.equals(baseAttrValue.getId())){
                            iterator.remove();

                            // 构造面包屑列表
                            BaseAttrValue baseAttrValueSelected = new BaseAttrValue();
                            baseAttrValueSelected.setValueName(baseAttrInfo.getAttrName()+":"+baseAttrValue.getValueName());
                            // 去除重复数据
                            String makeUrlParam = makeUrlParam(skuLsParams, valueId);
                            baseAttrValueSelected.setUrlParam(makeUrlParam);

                            baseAttrValuesList.add(baseAttrValueSelected);
                        }
                    }
                }
            }
        }

        //拼接路径
        String urlParam = makeUrlParam(skuLsParams);
        request.setAttribute("urlParam",urlParam);
        request.setAttribute("keyword",skuLsParams.getKeyword());
        request.setAttribute("baseAttrValuesList",baseAttrValuesList);

        request.setAttribute("totalPages", skuLsResult.getTotalPages());
        request.setAttribute("pageNo",skuLsParams.getPageNo());

        return "list";
    }



    public String makeUrlParam(SkuLsParams skuLsParam,String... excludeValueIds){
        String urlParam="";
        List<String> paramList = new ArrayList<>();
        if(skuLsParam.getKeyword()!=null){
            urlParam+="keyword="+skuLsParam.getKeyword();
        }
        if (skuLsParam.getCatalog3Id()!=null){
            if (urlParam.length()>0){
                urlParam+="&";
            }
            urlParam+="catalog3Id="+skuLsParam.getCatalog3Id();
        }
        // 构造属性参数
        if (skuLsParam.getValueId()!=null && skuLsParam.getValueId().length>0){
            for (int i=0;i<skuLsParam.getValueId().length;i++){
                String valueId = skuLsParam.getValueId()[i];
                if (excludeValueIds!=null && excludeValueIds.length>0){
                    String excludeValueId = excludeValueIds[0];
                    if (excludeValueId.equals(valueId)){
                        // 跳出代码，后面的参数则不会继续追加【后续代码不会执行】
                        // 不能写break；如果写了break；其他条件则无法拼接！
                        continue;
                    }
                }
                if (urlParam.length()>0){
                    urlParam+="&";
                }
                urlParam+="valueId="+valueId;
            }
        }
        return  urlParam;
    }
}




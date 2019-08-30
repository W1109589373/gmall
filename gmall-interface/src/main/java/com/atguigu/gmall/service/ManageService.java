package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManageService {



    //商品详情页展示, 根据skuId 获取商品详细信息
    public SkuInfo getSkuInfo(String skuId);

    /**
     * 根据spuId获取图片的集合
     * @param spuId
     * @return
     */
    public List<SpuImage> getSpuImageList(String spuId);

    /**
     * 保存spu_info
     */
    public void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 查询基本销售属性表
     */
    List<BaseSaleAttr> getBaseSaleAttrList();


    /**
     * 根据3级id 获取spu信息
     * @param catalog3Id
     * @return
     */
    public List<SpuInfo> getSpuList(String catalog3Id);

    /**
     * 获取一级目录
     * @return
     */
    public List<BaseCatalog1> getCatalog1();

    /**
     * 获取二级目录
     */
    public List<BaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 获取三级目录
     */
    public List<BaseCatalog3> getCatalog3(String catalog2Id);

    /**
     * 根据三级目录id获取平台属性
     * @param catalog3Id
     * @return
     */
    public List<BaseAttrInfo> getAttrList(String catalog3Id);
    //List<BaseAttrInfo> attrList =  manageService.getAttrList(attrValueIdList);
    public List<BaseAttrInfo> getAttrList(List<String> attrValueIdList);

    void manageService(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(String attrId);

    List<SpuSaleAttr> getspuSaleAttrList(String spuId);

    void saveSkuInfo(SkuInfo skuInfo);

    //获得商品详情页的销售属性
    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    //获取销售属性值拼接的集合
    List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId);
}

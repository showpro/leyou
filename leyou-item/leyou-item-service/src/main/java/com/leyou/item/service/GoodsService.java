package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @author zhan
 * @create 2019-05-04-22:11
 */
public interface GoodsService {
    /**
     * 所有商品相关的业务（包括SPU和SKU）放到一个业务下
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 新增商品
     * @param spuBo
     */

    void saveGoods(SpuBo spuBo);
    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 根据spuId查询Sku集合，进行修改页面回显
     * @param spuId
     * @return
     */
    List<Sku> querySkusBySpuId(Long spuId);

    /**
     * 回显后，更新商品信息
     * @param spuBo
     * @return
     */
    void updateGoods(SpuBo spuBo);
}

package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;

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
}

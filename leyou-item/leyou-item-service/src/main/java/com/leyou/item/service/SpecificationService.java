package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author zhan
 * @create 2019-05-04-18:16
 */
public interface SpecificationService {

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    List<SpecGroup> queryGroupsByCid(Long cid);

    /**
     * 根据条件查询规格参数
     *
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
}

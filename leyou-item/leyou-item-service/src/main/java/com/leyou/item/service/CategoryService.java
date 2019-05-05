package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhan
 * @create 2019-04-26-22:43
 */
public interface CategoryService {


    /**
     * 根据父节点id查询子节点分类
     * @param pid
     * @return
     */
    List<Category> queryCategoriesByPid(Long pid);

    /**
     * 根据多个分类id查询所有分类名称（这里是三级分类）
     * @param ids  分类id集合
     * @return
     */
    List<String> queryNamesByIds(List<Long> ids);

}

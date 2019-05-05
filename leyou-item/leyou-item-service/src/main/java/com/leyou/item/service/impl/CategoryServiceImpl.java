package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhan
 * @create 2019-04-26-22:43
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点id查询子节点分类
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid) {

        Category record = new Category();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    /**
     * 根据多个分类id查询所有分类名称（这里是三级分类）
     * @param ids
     * @return
     */
    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> categories = this.categoryMapper.selectByIdList(ids);
        //得到各级分类名称（处理旧的集合返回新的集合，用stream表达式,跟前端中的map函数用法类似）
        return categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}

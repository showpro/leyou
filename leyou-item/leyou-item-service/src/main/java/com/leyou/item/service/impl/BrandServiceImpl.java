package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author zhan
 * @create 2019-04-27-17:02
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {

        // 初始化example对象
        Example example = new Example(Brand.class);
        // 生成criteria对象用来添加查询条件
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);//满足条件之一，并
        }

        // 用PageHelper分页插件来添加分页条件
        PageHelper.startPage(page, rows);

        // 添加排序条件，先判断是否有排序字段
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        // 弄清为什么要选择selectByExample(example)方法查询？
        List<Brand> brands = this.brandMapper.selectByExample(example);
        // 包装成PageInfo分页对象，里面封装好了分页属性
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回，所以在PageResult对象中要先生成对应的构造方法
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增品牌
     * 这里要注意，我们不仅要新增品牌，还要维护品牌和商品分类的中间表。
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 先新增brand表
        this.brandMapper.insertSelective(brand);

        // 再新增中间表
        cids.forEach(cid -> {
            // 通用Mapper只能处理单表，这里调用了brandMapper中的一个自定义方法，来实现中间表的数据新增
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }

    /**
     * 根据分类id查询品牌列表
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandByCid(cid);
    }
}
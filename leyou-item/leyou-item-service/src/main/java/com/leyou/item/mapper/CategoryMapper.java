package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 接口继承通用Mapper,注意有两个依赖，别选错了
 * 在接口上加接口扫描 ：@Mapper注解，或者在引导类上加@MapperScan注解
 * SelectByIdListMapper<Category,Long>  根据多个id查询对象集合
 * @author zhan
 * @create 2019-04-26-22:34
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

}

package com.leyou.item.pojo;

import javax.persistence.*;
import java.util.List;

@Table(name = "tb_spec_group")
public class SpecGroup {

    @Id     // 映射主键属性
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 注解声明了主键的生成策略。GenerationType.IDENTITY 主键由数据库自动生成,主要是自动增长类型
    private Long id;

    private Long cid; // 分类id

    private String name; // 规格组的名称

    @Transient   // @Transient表示该属性并非一个到数据库表的字段的映射,表中如果没有该字段则ORM框架将忽略该属性.
    private List<SpecParam> params;

   // getter和setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecParam> getParams() {
        return params;
    }

    public void setParams(List<SpecParam> params) {
        this.params = params;
    }
}
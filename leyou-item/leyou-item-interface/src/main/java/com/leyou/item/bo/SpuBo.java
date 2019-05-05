package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;

/**
 * bo: bussines object 业务对象，没有表结构，是为了完成需求而增加的对象
 * @author zhan
 * @create 2019-05-04-22:28
 */
public class SpuBo extends Spu {

    String cname;// 商品分类名称

    String bname;// 品牌名称


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}

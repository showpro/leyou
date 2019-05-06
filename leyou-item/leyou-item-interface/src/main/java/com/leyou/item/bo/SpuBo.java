package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * bo: bussines object 业务对象，没有表结构，是为了完成需求而增加的对象
 * 可根据前端字段来定义
 * @author zhan
 * @create 2019-05-04-22:28
 */
public class SpuBo extends Spu {

    String cname;// 商品分类名称

    String bname;// 品牌名称

    SpuDetail spuDetail; // 商品详情

    List<Sku> skus; // sku列表

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

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

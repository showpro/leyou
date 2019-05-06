package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.pojo.Stock;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhan
 * @create 2019-05-04-22:13
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 根据条件分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        // 初始化
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();//条件查询对象

        // 添加查询条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        //添加上下架过滤条件
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }

        // 分页条件
        PageHelper.startPage(page, rows);

        // 执行查询，获取spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);

        // spu集合转化成spubo集合
        List<SpuBo> spuBos = new ArrayList<>();
        spus.forEach(spu->{
            SpuBo spuBo = new SpuBo();
            // copy共同属性的值到新的对象
            BeanUtils.copyProperties(spu, spuBo);
            // 查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "/"));//传入String类型的List集合，使用"/"号拼接  aa/bb/cc

            // 查询品牌的名称
            spuBo.setBname(this.brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());

            spuBos.add(spuBo);
        });
        // 返货pageResult<spuBo>
        return new PageResult<>(pageInfo.getTotal(), spuBos);

    }

    /**
     * 新增商品
     * 注意四张表的新增先后顺序，根据关联的id新增（tb_spu->tb_spu_detail->tb_sku->tb_stock）
     * @param spuBo
     */
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        // 新增spu
        // 设置默认字段
        spuBo.setId(null); //id设置为null,为了防止别人注入
        spuBo.setSaleable(true); //上架
        spuBo.setValid(true); //可用
        spuBo.setCreateTime(new Date()); //系统当前时间
        spuBo.setLastUpdateTime(spuBo.getCreateTime());//最后更新时间
        this.spuMapper.insertSelective(spuBo);//insertSelective()效率更高

        // 新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spuBo);
    }

    private void saveSkuAndStock(SpuBo spuBo) {
        // 传递的参数是skus集合，不能一次性新增多个，所以先遍历skus,每遍历一次新增一条记录
        spuBo.getSkus().forEach(sku -> {
            // 新增sku
            sku.setId(null); //防止注入
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);

            // 新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 根据spuId查询Sku集合，进行修改页面回显
     * @param spuId
     * @return
     */
    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);
        skus.forEach(sku -> {
            // 查询每一个sku中对应的库存信息，而且skuId就是tb_stock的主键
            Stock stock = this.stockMapper.selectByPrimaryKey(sku.getId());
            // 将库存信息封装进sku中
            sku.setStock(stock.getStock());
        });
        return skus;
    }

    /**
     * 回显后，更新商品信息
     * 4张表都有可能更新
     * @param spuBo
     * @return
     */
    @Transactional
    public void updateGoods(SpuBo spuBo) {
        // 根据spuId查询要删除的sku
        List<Sku> skus = this.querySkusBySpuId(spuBo.getId());
        /*Sku record = new Sku();
        record.setSpuId(spuBo.getId());
        List<Sku> skus = this.skuMapper.select(record);*/
        // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            /*List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前的stock库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);*/

            skus.forEach(sku -> {
                // 删除以前的stock库存
                this.stockMapper.deleteByPrimaryKey(sku.getId());
            });

            // 删除以前的sku
            Sku sku = new Sku();
            sku.setSpuId(spuBo.getId());
            this.skuMapper.delete(sku);

        }
        // 新增sku和库存
        saveSkuAndStock(spuBo);

        // 更新spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spuBo);

        // 更新spuDetail
        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }
}

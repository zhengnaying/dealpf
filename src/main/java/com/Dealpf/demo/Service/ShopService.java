package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {
    @Autowired
    ShopMapper shopMapper;
    //获取在售商品
    public List<Goods> getOnSaleGoods(){
        return this.shopMapper.getOnSaleGoods();
    }
    //uuid获取enter
    public EnterPrise getEnterByUuid(String enterUuid){
        return this.shopMapper.getEnterByUuid(enterUuid);
    }
    //获取所有商家名
    public List<String> getEnterName(){
        return this.shopMapper.getEnterName();
    }
    //获取商品图片
    public List<String> getPicture(String goodsUuid){
        return this.shopMapper.getPictureByUuid(goodsUuid);
    }
    //获取商品详情
    public Goods getGoodsDetail(String goodsName){
        return this.shopMapper.getGoodsDetail(goodsName);
    }
    //获取商品评论
    public List<Comment> getGoodsComment(String goodsUuid){
        return this.shopMapper.getGoodsComment(goodsUuid);
    }
    //商家名模糊查询
    public List<EnterPrise> getEnterListByName(String enterName) {
        return this.shopMapper.getEnterListByName(enterName);
    }
    //商品名模糊查询
    public List<Goods> getGoodsListByName(String goodsName) {
        return this.shopMapper.getGoodsListByName(goodsName);
    }
    //商品分类查询
    public List<Goods> getGoodsByType(String goodstype){
        return this.shopMapper.getGoodsByType(goodstype);
    }
    //获取所有商品
    public List<Goods> getWholeGoods(String enterUuid){
        return this.shopMapper.getWholeGoods(enterUuid);
    }
    public User getUser(String userUuid){
        return this.shopMapper.getUser(userUuid);
    }
}

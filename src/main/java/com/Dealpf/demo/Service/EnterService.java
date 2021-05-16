package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Mapper.EnterMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnterService {
    @Autowired
    EnterMapper enterMapper;
    //获取商品列表
    public List<Goods> getGoodsList(){
        return this.enterMapper.getGoodList();
    }
    //获取指定商品
    public Goods getGood(String goodsName){
        return this.enterMapper.getGood(goodsName);
    }
    //获取指定审核商品
    public Goods searchGoodsRequest(String goodsName){
        return enterMapper.searchGoodRequest(goodsName);
    }
    //提交商品审核
    public void goodsRequest(String goodsName,String goodsType,double goodsPrice,String goodsSize,int goodsStock,String goodsIntroduction,String goodsBargain,
                              String goodsState,double goodsDiscount,String enterUuid){
        this.enterMapper.goodsRequest(goodsName,goodsType,goodsPrice,goodsSize,goodsStock,goodsIntroduction,goodsBargain,goodsState,goodsDiscount,enterUuid);
    }
    //获取商家uuid
    public String getEnterUuid(String enterName){
        return this.enterMapper.getEnterUuid(enterName);
    }
    //获取某审核商品图片列表
    public ArrayList<Picture_temp> getPicture_temp(String goodsName){
        return this.enterMapper.getPicture_temp(goodsName);
    }
   //添加商品审核照片
    public void insertPictureTemp(String goodsName,String path){
        this.enterMapper.insertPictureTemp(goodsName,path);
    }
    //删除商品审核照片
    public void deletePictureTemp(String goodsName){
        this.enterMapper.deletePictureTemo(goodsName);
    }
    //下架商品
    public void dropGoods(String goods_name){
        this.enterMapper.dropGoods(goods_name);
    }
    //上架商品
    public void updateGoods(String goods_name){
        this.enterMapper.updateGoods(goods_name);
    }
    //获取已上架商品
    public List<Goods> getOnSaleGoods(String enterUuid){
        return this.enterMapper.getOnSaleGoods(enterUuid);
    }

    //获取已下架商品
    public List<Goods> getDroppedGoods(String enterUuid){
        return this.enterMapper.getDroppedGoods(enterUuid);
    }
    //获取已售罄商品
    public List<Goods> getSoldOutGoods(){
        return this.enterMapper.getSoldOutGoods();
    }
    //获取商家钱包
    public Wallet_enter getWalletEnter(String enterUuid){
        return this.enterMapper.getWalletEnter(enterUuid);
    }
    //获取商家钱包明细
    public List<Transaction_enter> getTransactionEnter(String enterUuid){
        return this.enterMapper.getTransactionEnter(enterUuid);
    }
    public List<Deal> getRefundRequest(String enterUuid){
        return this.enterMapper.getRefundRequest(enterUuid);
    }
    public EnterPrise getEnter(String enterUuid){
        return this.enterMapper.getEnter(enterUuid);
    }
    public Goods getGoodByUuid(String goodsUuid){
        return this.enterMapper.getGoodByUuid(goodsUuid);
    }
    public User getUserByUuid(String userUuid){
        return this.enterMapper.getUserByUuid(userUuid);
    }

}


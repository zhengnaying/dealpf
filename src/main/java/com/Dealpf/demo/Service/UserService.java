package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Mapper.UserMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //商品加入购物车
    public void insertCar(String goodsUuid,String userUuid,int goodCount,int goodStatus){
        this.userMapper.insertCar(goodsUuid,userUuid,goodCount,goodStatus);
    }
    //获取购物车
    public List<Shop_car> getCar(String userUuid){
        return this.userMapper.getCar(userUuid);
    }
    //获取指定商品
    public Goods getGood(String goodsUuid){
        return this.userMapper.getGood(goodsUuid);
    }
    //获取指定用户
    public User getUser(int userId){
        return this.userMapper.getUser(userId);
    }
    //获取指定用户
    public User getUserByUuid(String userUuid){
        return this.userMapper.getUserByUuid(userUuid);
    }
    //用户评论商品
    public void insertComment(String commentContent,String userUuid,String goodsUuid,String dealUuid){
        this.userMapper.insertComment(commentContent,userUuid,goodsUuid,dealUuid);
    }
    //用户更新评论商品
    public void updateComment(String dealUuid,String commentContent){
        this.userMapper.updateComment(dealUuid,commentContent);
    }
    //用户打分商品
    public void assessGoods(String goodsUuid,double goodsScore){
        this.userMapper.assessGoods(goodsUuid,goodsScore);
    }
    //设置商品用户好评数量
    public void setGoodsPraiseCount(String goodsUuid,int goodsPraiseCount){
        this.userMapper.setGoodsPraiseCount(goodsUuid,goodsPraiseCount);
    }
    //更新商品好评率
    public void updateGoodsPraiseRate(String goodUuid,double goodsPraiseRate){
        this.userMapper.updateGoodsPraiseRate(goodUuid,goodsPraiseRate);
    }
    //更新销量
    public void setGoodsVolume(String goodsUuid,int goodsVolume){
        this.userMapper.setGoodsVolume(goodsUuid,goodsVolume);
    }
    //更新库存
    public void updateGoodsStock(String goodsUuid,int goodsStock){
        this.userMapper.updateGoodsStock(goodsUuid,goodsStock);
    }
    //更新状态
    public void updateGoodsFlag(String goodsUuid,int goodsFlag){
        this.userMapper.updateGoodsFlag(goodsUuid,goodsFlag);
    }
    //添加订单
    public void insertDeal(String goodsUuid,String userUuid,String enterUuid,int dealCount,double dealAmount,int dealStatus){
       this.userMapper.insertDeal(goodsUuid,userUuid,enterUuid,dealCount,dealAmount,dealStatus);
    }
    //获取商品购买数量
    public Deal getDeal(String dealUuid){
        return this.userMapper.getDeal(dealUuid);
    }
    //修改订单数量
    public void updateDealAssess(String dealUuid,double dealAssess){
        this.userMapper.setDealAssess(dealUuid,dealAssess);
    }
    //获取评论
    public Comment getCommentByDeal(String dealUuid){
        return this.userMapper.getCommentByUuid(dealUuid);
    }
    //添加购物车
    public void insertShopCar(String goodsUuid,String userUuid,int goodCount,int goodStatus){
        this.userMapper.createShopCar(goodsUuid,userUuid,goodCount,goodStatus);
    }
    //获取用户钱包
    public Wallet_user getWalletUser(int userId){
        return this.userMapper.getWalletUser(userId);
    }
    //修改钱包当前金额
    public void setCurrentMoney(double currentMoney,int userId){
        this.userMapper.setCurrentMoney(currentMoney,userId);
    }
    //获取商家
    public EnterPrise getEnterByUuid(String enterUuid) {
        return this.userMapper.getEnterByUuid(enterUuid);
    }
    //获取商品图片
    public List<String> getPictureByUuid(String goodsUuid){
        return this.userMapper.getPictureByUuid(goodsUuid);
    }
    //获取购物车中的某商品
    public Shop_car getCarByGoods(String goodsUuid){
        return this.userMapper.getCarByGoods(goodsUuid);
    }
    //更新购物车
    public void updateCar(String goodsUuid,int goodCount){
        this.userMapper.updateCar(goodsUuid,goodCount);
    }
    //更新用户积分
    public void setUserIntergral(int userIntergral,int userID){
        this.userMapper.setUserIntergral(userIntergral,userID);
    }
    //更新商家钱包
    public void setWalletAmount(double walletAmount,String enterUuid){
        this.userMapper.setWalletAmount(walletAmount,enterUuid);
    }
    //获取商家钱包
    public Wallet_enter getWalletEnter(String enterUuid){
        return this.userMapper.getWalletEnter(enterUuid);
    }
    //购物车修改数量
    public void setGoodCount(int goodCount,int carId){
        this.userMapper.setGoodCount(goodCount,carId);
    }
    //购物车删除
    public  void deleteCar(int carId){
        this.userMapper.deleteCar(carId);
    }
    //获取单个购物车
    public Shop_car getCarById(int carId){
      return  this.userMapper.getCarById(carId);
    }
    //创建交易详情
    public void createTrasaction(String enterUuid,String tranStart, String tranArrive,double tranAmount,int tranType){
        this.userMapper.createTrasaction(enterUuid,tranStart,tranArrive,tranAmount,tranType);
    }
    //获取用户订单
    public List<Deal> getUserDeal(String userUuid){
        return this.userMapper.getUserDeal(userUuid);
    }
    //用户打分商家
    public void assessEnter(String enterUuid,double enterScore){
        this.userMapper.assessEnter(enterUuid,enterScore);
    }
    //设置商家用户好评数量
    public void setEnterPraiseCount(String enterUuid,int enterPraiseCount){
        this.userMapper.setEnterPraiseCount(enterUuid,enterPraiseCount);
    }
    //更新商家好评率
    public void updateEnterPraiseRate(String enterUuid,double enterPraiseRate){
        this.userMapper.updateEnterPraiseRate(enterUuid,enterPraiseRate);
    }
    //更新商家销量
    public void setEnterDealCount(String enterUuid,int enterDealCount){
        this.userMapper.setEnterDealCount(enterUuid,enterDealCount);
    }
    //修改订单状态
    public void setDealStatus(String dealUuid,int dealStatus){
        this.userMapper.setDealStatus(dealUuid,dealStatus);
    }
    //添加系统交易明细
    public void addSystemTran(String dealUuid,String tranStart, String tranArrived,double walletAmount,int tranType){
        this.userMapper.addSystemTran(dealUuid,tranStart,tranArrived,walletAmount,tranType);
    }
   //系统收钱
   public void setWalletSystem(double walletAmount){
        this.userMapper.setWalletSystem(walletAmount);
   }
   //获取系统钱数
    public double getSystemAmount(){
        return this.userMapper.getSystemAmount();
    }
    //获取最新订单
    public List<Deal> getDealLast(int listLenth){
        return this.userMapper.getDealLast(listLenth);
    }
}


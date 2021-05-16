package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    //获取用户列表
    public List<User> getUserList() {
        return adminMapper.getUserList();
    }

    //获取商家列表
    public List<EnterPrise> getEnterList() {
        return adminMapper.getEnterList();
    }

    //获取用户注册申请列表
    public List<User> getUserRegist() {
        return adminMapper.getUserRegist();
    }

    //获取商家注册申请列表
    public List<EnterPrise> getEnterRegist() {
        return adminMapper.getEnterRegist();
    }

    //获取商品上传申请列表
    public List<Goods> getGoodsRequest() {
        return adminMapper.getGoodsRequest();
    }

    //查询指定用户
    public User getUserByName(String userName) {
        return adminMapper.getUserByName(userName);
    }

    //查询指定商家
    public EnterPrise getEnterByName(String enterName) {
        return adminMapper.getEnterByName(enterName);
    }

    //模糊查询
    public List<User> getUserListByName(String userName) {
        return adminMapper.getUserListByName(userName);
    }

    public List<EnterPrise> getEnterListByName(String enterName) {
        return adminMapper.getEnterListByName(enterName);
    }

    //删除用户
    public void delectUser(String userName) {
        adminMapper.deleteUser(userName);
    }
    public void deleteWalletUser(int userId){adminMapper.deleteWalletUser(userId);}
    public void deleteUserTran(String userUuid){adminMapper.deleteUserTran(userUuid);}
    public void deleteShopCar(String userUuid){adminMapper.deleteShopCar(userUuid);}

    public void delectEnter(String enterName) {
        adminMapper.deleteEnter(enterName);
    }
    public void deleteWalletEnter(String enterUuid){adminMapper.deleteWalletEnter(enterUuid);}
    public void deleteTransactionEnter(String enterUuid){
        adminMapper.deleteTransactionEnter(enterUuid);
    }
    public List<Goods> getEnterGoodsRequest(String enterUuid){
        return adminMapper.getEnterGoodsRequest(enterUuid);
    }
    public List<Goods> getEnterGoods(String enterUuid){return adminMapper.getEnterGoods(enterUuid);}
    public void deleteShopCarGoods(String goodsUuid){adminMapper.deleteShopCarGoods(goodsUuid);}
    public void deletePicture(String goodsUuid){adminMapper.deletePicture(goodsUuid);}
    public void deleteGoods(String goodsUuid){adminMapper.deleteGoods(goodsUuid);}
    public void deleteComment(String goodsUuid){adminMapper.deleteComment(goodsUuid);}
    //更新用户权限
    public void updateUser(String userName, String data) {
        adminMapper.update(userName, data);
    }

    //更新商家登记
    public void updateGrade(String enterName, int enterGrade) {
        adminMapper.updateGrade(enterName, enterGrade);
    }

    //上传商品
    public void insertGoods(String goodsName, String goodsType, double goodsPrice, String goodsSize, int goodsStock, String goodsIntroduction, String goodsBargain,
                            String goodsState, double goodsScore, int goodsFlag, int goodsVolume, double goodsPraiseRate, double goodsDiscount, double goodsPraseCount, String enterUuid, double goodsCurrentPrice) {
        adminMapper.insertGoods(goodsName, goodsType, goodsPrice, goodsSize, goodsStock, goodsIntroduction, goodsBargain, goodsState, goodsScore, goodsFlag, goodsVolume, goodsPraiseRate, goodsDiscount, goodsPraseCount, enterUuid, goodsCurrentPrice);
    }

    //商品名获取商品申请
    public Goods getGoodsRequestByName(String goodsName) {
        return adminMapper.getGoodsRequestByName(goodsName);
    }

    //删除商品申请
    public void deleteGoodsRequest(String goodsName) {
        adminMapper.deleteGoodsRequest(goodsName);
    }

    //商品名获取商品
    public Goods getGoodsByName(String goodsName) {
        return adminMapper.getGoodsByName(goodsName);
    }

    //获取商品列表
    public List<Goods> getGoodsList() {
        return adminMapper.getGoods();
    }

    public EnterPrise getEnterRequestByName(String enterName) {
        return this.adminMapper.getEnterRequestByName(enterName);
    }

    public ArrayList<Picture_temp> getPictureTempByName(String goodsName) {
        return this.adminMapper.getPictureTempByName(goodsName);
    }

    public void insertPicture(String path, String goodsUuid) {
        this.adminMapper.insertPicture(path, goodsUuid);
    }

    public void deletePictureTemp(String goodsName) {
        this.adminMapper.deletePictureTemp(goodsName);
    }

    public String getEnterName(String enterUuid) {
        return this.adminMapper.getEnterName(enterUuid);
    }
    //获取系统交易明细
    public List<Transaction_system> getSystemTran(){
        return this.adminMapper.getSystemTran();
    }
    public void setCurrentMoney(double currentMoney,int userId){
        this.adminMapper.setCurrentMoney(currentMoney,userId);
    }
    public double getCurrent_money(int userId){
        return this.adminMapper.getCurrent_money(userId);
    }
    public EnterPrise getEnterByUuid(String enterUuid){
        return this.adminMapper.getEnterByUuid(enterUuid);
    }
    public User getUserByUuid(String userUuid){
        return this.adminMapper.getUserByUuid(userUuid);
    }
    public Deal getDeal(String dealUuid){return this.adminMapper.getDeal(dealUuid);}
    public Wallet_system getWalletSystem(){
        return this.adminMapper.getWalletSystem();
    }
}

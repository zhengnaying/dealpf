package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO Shop_car (Goods_uuid,User_uuid,Good_count,Good_status) VALUES (#{goodsUuid}," +
            "#{userUuid},#{goodCount},#{goodStatus})")
    public void insertCar(String goodsUuid,String userUuid,int goodCount,int goodStatus);
    @Select("SELECT * FROM Shop_car WHERE User_uuid = #{userUuid}")
    public List<Shop_car> getCar(String userUuid);
    @Select("SELECT * FROM Shop_car WHERE Car_id = #{carId}")
    public Shop_car getCarById(int carId);
    @Select("SELECT * FROM Shop_car WHERE Goods_uuid = #{goodsUuid}")
    public Shop_car getCarByGoods(String goodsUuid);
    @Update("UPDATE Shop_car SET Good_count = #{goodCount} WHERE (Goods_uuid = #{goodsUuid});")
    public void updateCar(String goodsUuid,int goodCount);
    @Select("SELECT * FROM Goods WHERE Goods_uuid = #{goodsUuid}")
    public Goods getGood(String goodsUuid);
    @Select("SELECT * FROM User WHERE User_id = #{userId}")
    public User getUser(int UserId);
    @Select("SELECT * FROM User WHERE User_uuid = #{userUuid}")
    public User getUserByUuid(String UserUuid);
    @Insert("INSERT INTO Comment (Comment_content,User_uuid,Goods_uuid,Deal_uuid) VALUES (#{commentContent}," +
            "#{userUuid},#{goodsUuid},#{dealUuid})")
    public void insertComment(String commentContent,String userUuid,String goodsUuid,String dealUuid);
    @Update("UPDATE Comment SET Comment_content = #{commentContent} WHERE (Deal_uuid = #{dealUuid});")
    public void updateComment(String dealUuid,String commentContent);
    @Update("UPDATE Goods SET Goods_score = #{goodsScore} WHERE (Goods_uuid = #{goodsUuid});")
    public void assessGoods(String goodsUuid,double goodsScore);
    @Update("UPDATE Goods SET Goods_praise_count = #{goodsPraiseCount} WHERE (Goods_uuid = #{goodsUuid});")
    public void setGoodsPraiseCount(String goodsUuid,int goodsPraiseCount);
    @Update("UPDATE Goods SET Goods_praise_rate = #{goodsPraiseRate} WHERE (Goods_uuid = #{goodsUuid});")
    public void updateGoodsPraiseRate(String goodsUuid,double goodsPraiseRate);
    @Update("UPDATE Goods SET Goods_stock = #{goodsStock} WHERE (Goods_uuid = #{goodsUuid});")
    public void updateGoodsStock(String goodsUuid,int goodsStock);
    @Update("UPDATE Goods SET Goods_flag = #{goodsFlag} WHERE (Goods_uuid = #{goodsUuid});")
    public void updateGoodsFlag(String goodsUuid,int goodsFlag);
    @Update("UPDATE Goods SET Goods_volume = #{goodsVolume} WHERE (Goods_uuid = #{goodsUuid});")
    public void setGoodsVolume(String goodsUuid,int goodsVolume);
    @Insert("INSERT INTO Deal (Deal_uuid,Goods_uuid,User_uuid,Enter_uuid,Deal_count,Deal_amount,Deal_status) VALUES (#{dealUuid},#{goodsUuid}," +
            "#{userUuid},#{enterUuid},#{dealCount},#{dealAmount},#{dealStatus})")
    @SelectKey(keyProperty = "dealUuid", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    public void insertDeal(String goodsUuid,String userUuid,String enterUuid,int dealCount,double dealAmount ,int dealStatus);
    @Select("SELECT * FROM Deal order by Deal_time desc limit #{listLength};")
    public List<Deal> getDealLast(int listLenth);
    @Select("SELECT * FROM Deal WHERE Deal_uuid = #{dealUuid} ")
    public Deal getDeal(String dealUuid);
    @Select("SELECT * FROM Deal WHERE User_uuid = #{userUuid} order by Deal_time desc")
    public List<Deal> getUserDeal(String userUuid);
    @Update("UPDATE Deal SET Deal_assess = #{dealAssess} WHERE (Deal_uuid = #{dealUuid});")
    public void setDealAssess(String dealUuid,double dealAssess);
    @Update("UPDATE Deal SET Deal_status = #{dealStatus} WHERE (Deal_uuid = #{dealUuid});")
    public void setDealStatus(String dealUuid,int dealStatus);
    @Select("SELECT * FROM Comment WHERE Deal_uuid = #{dealUuid}")
    public Comment getCommentByUuid(String dealUuid);
    @Insert("INSERT INTO Shop_car (Goods_uuid,User_uuid,Good_count,Good_status) VALUES (#{goodsUuid},#{userUuid}," +
            "#{goodCount},#{goodStatus})")
    void createShopCar(String goodsUuid,String userUuid,int goodCount,int goodStatus);
    @Update("UPDATE Shop_car SET Good_count= #{goodCount} WHERE (Car_id=#{carId});")
    public void setGoodCount(int goodCount,int carId);
    @Delete("DELETE FROM Shop_car WHERE (Car_id=#{carId}) ;")
    void deleteCar(int carId);
    @Select("SELECT * FROM Wallet_user WHERE User_id = #{userId}")
    public Wallet_user getWalletUser(int userId);
    @Select("SELECT * FROM Wallet_enter WHERE Enter_uuid = #{enterUuid}")
    public Wallet_enter getWalletEnter(String enterUuid);
    @Update("UPDATE Wallet_user SET Current_money= #{currentMoney} WHERE (User_id = #{userId});")
    public void setCurrentMoney(double currentMoney,int userId);
    @Update("UPDATE Wallet_user SET User_intergral= #{userIntergral} WHERE (User_id = #{userId});")
    public void setUserIntergral(int userIntergral,int userId);
    @Update("UPDATE Wallet_enter SET Wallet_amount= #{walletAmount} WHERE (Enter_uuid = #{enterUuid});")
    public void setWalletAmount(double walletAmount,String enterUuid);
    @Select("SELECT * FROM Enterprise WHERE Enter_uuid = #{enterUuid}")
    public EnterPrise getEnterByUuid(String enterUuid);
    @Select("SELECT P_path FROM Picture WHERE Goods_uuid = #{goodsUuid}")
    public List<String> getPictureByUuid(String goodsUuid);
    @Insert("INSERT INTO Transaction_enter (Enter_uuid,Tran_start,Tran_arrive,Tran_amount,Tran_type) VALUES (#{enterUuid}," +
            "#{tranStart},#{tranArrive},#{tranAmount},#{tranType})")
    public void createTrasaction(String enterUuid,String tranStart, String tranArrive,double tranAmount,int tranType);
    @Update("UPDATE EnterPrise SET Enter_score = #{enterScore} WHERE (Enter_uuid = #{enterUuid});")
    public void assessEnter(String enterUuid,double enterScore);
    @Update("UPDATE EnterPrise SET Enter_praise_count = #{enterPraiseCount} WHERE (Enter_uuid = #{enterUuid});")
    public void setEnterPraiseCount(String enterUuid,int enterPraiseCount);
    @Update("UPDATE EnterPrise SET Enter_praise_rate = #{enterPraiseRate} WHERE (Enter_uuid = #{enterUuid});")
    public void updateEnterPraiseRate(String enterUuid,double enterPraiseRate);
    @Update("UPDATE EnterPrise SET Enter_deal_count = #{enterDealCount} WHERE (Enter_uuid = #{enterUuid});")
    public void setEnterDealCount(String enterUuid,int enterDealCount);
    @Insert("INSERT INTO Transaction_system (Deal_uuid,get_time,post_time,Tran_amount,Tran_type) VALUES (#{dealUuid}," +
            "#{getTime},#{postTime},#{tranAmount},#{tranType})")
    public void addSystemTran(String dealUuid,String getTime, String postTime,double tranAmount,int tranType);
    @Update("UPDATE Wallet_system SET Wallet_amount= #{walletAmount} WHERE Wallet_id = 1;")
    public void setWalletSystem(double walletAmount);
    @Select("SELECT Wallet_amount FROM Wallet_system WHERE Wallet_id = 1;")
    public double getSystemAmount();
}


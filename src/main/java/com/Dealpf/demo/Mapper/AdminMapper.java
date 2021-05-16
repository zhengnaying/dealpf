package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AdminMapper {
    //查询所有用户
    @Select("SELECT * FROM User where User_role = '普通用户';")
    List<User> getUserList();
    //查询所有商家
    @Select("SELECT * FROM EnterPrise")
    List<EnterPrise> getEnterList();
    //用户名查询用户
    @Select("SELECT * FROM User WHERE User_name = #{userName}")
    User getUserByName(String userName);
    @Select("SELECT * FROM User WHERE User_uuid = #{userUuid}")
    User getUserByUuid(String userUuid);
    @Select("SELECT * FROM Deal WHERE Deal_uuid = #{dealUuid}")
    Deal getDeal(String dealUuid);
    //用户名查询商家
    @Select("SELECT * FROM EnterPrise WHERE Enter_name = #{enterName}")
    EnterPrise getEnterByName(String enterName);
    @Select("SELECT * FROM EnterPrise WHERE Enter_uuid = #{enterUuid}")
    EnterPrise getEnterByUuid(String enterUuid);
    @Select("SELECT * FROM Enter_request WHERE Enter_name = #{enterName}")
    EnterPrise getEnterRequestByName(String enterName);
    //用户名模糊查询
    @Select("SELECT * FROM User WHERE User_name Like #{userName}")
    List<User> getUserListByName(String userName);
    //商家名模糊查询
    @Select("SELECT * FROM EnterPrise WHERE User_name Like #{enterName}")
    List<EnterPrise> getEnterListByName(String enterName);
    //删除指定用户
    @Delete("DELETE FROM User WHERE User_name = #{userName} ;")
    void deleteUser(String userName);
    @Delete("DELETE FROM Wallet_user WHERE User_id = #{userId} ;")
    void deleteWalletUser(int userId);
    @Delete("DELETE FROM Transaction_user WHERE User_uuid = #{userUuid};")
    void deleteUserTran(String userUuid);
    @Delete("DELETE FROM Shop_car WHERE User_uuid = #{userUuid};")
    void deleteShopCar(String userUuid);
    //删除指定商家
    @Delete("DELETE FROM EnterPrise WHERE Enter_name = #{enterName} ;")
    void deleteEnter(String enterName);
    @Delete("DELETE FROM Wallet_enter WHERE Enter_uuid = #{enterUuid} ;")
    void deleteWalletEnter(String enterUuid);
    @Delete("DELETE FROM Transaction_enter WHERE Enter_uuid = #{enterUuid} ;")
    void deleteTransactionEnter(String enterUuid);
    @Select("SELECT * FROM Goods_request WHERE Enter_uuid = #{enterUuid};")
    List<Goods> getEnterGoodsRequest(String enterUuid);
    @Select("SELECT * FROM Goods WHERE Enter_uuid = #{enterUuid};")
    List<Goods> getEnterGoods(String enterUuid);
    @Delete("DELETE FROM Shop_car WHERE Goods_uuid = #{goodsUuid} ;")
    void deleteShopCarGoods(String goodsUuid);
    @Delete("DELETE FROM Comment WHERE Goods_uuid = #{goodsUuid} ;")
    void deleteComment(String goodsUuid);
    @Delete("DELETE FROM Picture WHERE Goods_uuid = #{goodsUuid} ;")
    void deletePicture(String goodsUuid);
    @Delete("DELETE FROM Goods WHERE Goods_uuid = #{goodsUuid} ;")
    void deleteGoods(String goodsUuid);
    //获取申请用户
    @Select("SELECT * FROM User_request where User_role = '普通用户';")
    List<User> getUserRegist();
    //获取申请商家
    @Select("SELECT * FROM Enter_request;")
    List<EnterPrise> getEnterRegist();
    //修改用户数据
    @Update("UPDATE User SET User_role = #{userRole} WHERE (User_name = #{userName});")
    void update(String userName,String userRole);
    //修改商家等级
    @Update("UPDATE EnterPrise SET Enter_grade = #{enterGrade} WHERE (Enter_name = #{enterName});")
    void updateGrade(String enterName,int enterGrade);
    //获取商品列表
    @Select("SELECT * FROM Goods;")
    List<Goods> getGoods();
    //获取商品申请列表
    @Select("SELECT * FROM Goods_request;")
    List<Goods> getGoodsRequest();
    //查询商品申请
    @Select("SELECT * FROM Goods_request WHERE Goods_name = #{goodsName};")
    Goods getGoodsRequestByName(String goodsName);
    //删除商品申请
    @Delete("DELETE FROM Goods_request WHERE Goods_name = #{goodsName} ;")
    void deleteGoodsRequest(String goodsName);
    //删除商品申请图片
    @Delete("DELETE FROM Picture_temp WHERE Goods_name = #{goodsName} ;")
    void deletePictureTemp(String goodsName);
   //同意上传商品
    @Insert("INSERT INTO Goods (Goods_name,Goods_type,Goods_price,Goods_size,Goods_stock,"+
            "Goods_introduction,Goods_bargain,Goods_state,Goods_score,Goods_flag,Goods_volume,Goods_praise_rate,Goods_uuid,Goods_discount,Goods_praise_count,Enter_uuid,Goods_currentPrice)" +
            "VALUES (#{goodsName},#{goodsType},#{goodsPrice},#{goodsSize},#{goodsStock},#{goodsIntroduction},#{goodsBargain}," +
            "#{goodsState},#{goodsScore},#{goodsFlag},#{goodsVolume},#{goodsPraiseRate},#{goodsUuid},#{goodsDiscount},#{goodsPraiseCount},#{enterUuid},#{goodsCurrentPrice})")
    @SelectKey(keyProperty = "goodsUuid", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    void insertGoods(String goodsName,String goodsType,double goodsPrice,String goodsSize,int goodsStock,String goodsIntroduction,String goodsBargain,
                    String goodsState,double goodsScore,int goodsFlag,int goodsVolume,double goodsPraiseRate,double goodsDiscount,double goodsPraiseCount,String enterUuid,double goodsCurrentPrice);
    //上传商品图片
    @Insert("INSERT INTO Picture (P_path,Goods_uuid) VALUES (#{path},#{goodsUuid})")
    void insertPicture(String path,String goodsUuid);

    //查询商品
    @Select("SELECT * FROM Goods WHERE Goods_name = #{goodsName};")
    Goods getGoodsByName(String goodsName);
    //查询商品审核图片
    @Select("SELECT * FROM Picture_temp WHERE Goods_name = #{goodsName};")
    ArrayList<Picture_temp> getPictureTempByName(String goodsName);
    //查询商家名称
    @Select("SELECT Enter_name FROM EnterPrise WHERE Enter_uuid = #{enterUuid};")
    String getEnterName(String enterUuid);
    @Select("SELECT * FROM Transaction_system")
    public List<Transaction_system> getSystemTran();
    @Update("UPDATE Wallet_user SET Current_money= #{currentMoney} WHERE (User_id = #{userId});")
    public void setCurrentMoney(double currentMoney,int userId);
    @Select("SELECT Current_money FROM Wallet_user WHERE User_id = #{userId}")
    public double getCurrent_money(int userId);
    @Select("SELECT * FROM Wallet_system Where Wallet_id = 1")
    public Wallet_system getWalletSystem();
}

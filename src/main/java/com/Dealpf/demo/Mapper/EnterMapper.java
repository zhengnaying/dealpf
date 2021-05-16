package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface EnterMapper {
    @Select("SELECT * FROM Goods")
    public List<Goods> getGoodList();

    @Select("SELECT * FROM Goods WHERE Goods_flag = 1 and Enter_uuid = #{enterUuid}")
    public List<Goods> getOnSaleGoods(String enterUuid);

    @Select("SELECT * FROM Goods WHERE Goods_flag = 0 and Enter_uuid = #{enterUuid}")
    public List<Goods> getDroppedGoods(String enterUuid);

    @Select("SELECT * FROM Goods WHERE Goods_flag = -1")
    public List<Goods> getSoldOutGoods();

    @Select("SELECT * FROM Goods WHERE Goods_name = #{goodsName}")
    public Goods getGood(String goodsName);

    @Select("SELECT * FROM Goods WHERE Goods_uuid = #{goodsUuid}")
    public Goods getGoodByUuid(String goodsUuid);
    @Select("SELECT * FROM User WHERE User_uuid = #{userUuid}")
    public User getUserByUuid(String userUuid);
    @Select("SELECT * FROM Goods_request WHERE Goods_name = #{goodsName}")
    public Goods searchGoodRequest(String goodsName);

    @Insert("INSERT INTO Goods_request (Goods_name,Goods_type,Goods_price,Goods_size,Goods_stock," +
            "Goods_introduction,Goods_bargain,Goods_state,Goods_discount,Enter_uuid) " +
            "VALUES (#{goodsName},#{goodsType},#{goodsPrice},#{goodsSize},#{goodsStock},#{goodsIntroduction},#{goodsBargain}," +
            "#{goodsState},#{goodsDiscount},#{enterUuid});")
    public void goodsRequest(String goodsName,String goodsType,double goodsPrice,String goodsSize,int goodsStock,String goodsIntroduction,String goodsBargain,
                              String goodsState,double goodsDiscount,String enterUuid);
    @Select("SELECT * FROM EnterPrise WHERE Enter_name = #{enterName}")
    public String getEnterUuid(String enterName);
    @Select("SELECT * FROM EnterPrise WHERE Enter_uuid = #{enterUuid}")
    public EnterPrise getEnter(String enterUuid);
    @Select("SELECT * FROM Picture_temp WHERE Goods_name = #{goodsName}")
    public ArrayList<Picture_temp> getPicture_temp(String goodsName);

    @Insert("INSERT INTO Picture_temp (Goods_name,P_path) VALUES (#{goodsName},#{path})")
    public void insertPictureTemp(String goodsName,String path);

    @Delete("DELETE FROM Picture_temp WHERE Goods_name = #{goodsName}")
    public void deletePictureTemo(String goodsName);

    @Update("UPDATE Goods SET Goods_flag = 0 WHERE (Goods_name = #{goodsName});")
    public void dropGoods(String goodsName);

    @Update("UPDATE Goods SET Goods_flag = 1 WHERE (Goods_name = #{goodsName});")
    public void updateGoods(String goodsName);

    @Select("SELECT * FROM Wallet_enter WHERE Enter_uuid = #{enterUuid}")
    public Wallet_enter getWalletEnter(String enterUuid);
    @Select("SELECT * FROM Transaction_enter WHERE Enter_uuid = #{enterUuid} order by Tran_id desc")
    public List<Transaction_enter> getTransactionEnter(String enterUuid);
    @Select("SELECT * FROM Deal WHERE Deal_status = 2 and Enter_uuid = #{enterUuid} order by Deal_time")
    public List<Deal> getRefundRequest(String enterUuid);

}

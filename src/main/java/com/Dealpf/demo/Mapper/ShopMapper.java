package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.Comment;
import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.Goods;
import com.Dealpf.demo.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {
    //获取已上架商品列表
    @Select("SELECT * FROM Goods WHERE Goods_flag = 1")
    public List<Goods> getOnSaleGoods();
    @Select("SELECT * FROM Goods WHERE Enter_uuid = #{enterUuid}")
    public List<Goods> getWholeGoods(String enterUuid);
    @Select("SELECT * FROM User WHERE User_uuid = #{userUuid}")
    public User getUser(String userUuid);
    //uuid获取商家
    @Select("SELECT * FROM Enterprise WHERE Enter_uuid = #{enterUuid}")
    public EnterPrise getEnterByUuid(String enterUuid);
    @Select("SELECT Enter_name FROM Enterprise")
    public List<String> getEnterName();
    @Select("SELECT P_path FROM Picture WHERE Goods_uuid = #{goodsUuid}")
    public List<String> getPictureByUuid(String goodsUuid);
    @Select("SELECT * FROM Goods WHERE Goods_name = #{goodsName}")
    public Goods getGoodsDetail(String goodsName);
    @Select("SELECT * FROM Goods WHERE Goods_type = #{goodstype} and Goods_flag = 1")
    public List<Goods> getGoodsByType(String goodstype);
    @Select("SELECT * FROM Comment WHERE Goods_uuid = #{goodsUuid}")
    public List<Comment> getGoodsComment(String goodsUuid);
    //商家名模糊查询商家
    @Select("SELECT * FROM EnterPrise WHERE Enter_name Like #{enterName}")
    public List<EnterPrise> getEnterListByName(String enterName);
    //商品名模糊查询
    @Select("SELECT * FROM Goods WHERE Goods_name Like #{goodsName} and Goods_flag = 1")
    public List<Goods> getGoodsListByName(String goodsName);
}


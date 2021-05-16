package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class Goods {
    private int Goods_id;
    private String Goods_name;
    private String Goods_type;
    private double Goods_price;
    private double Goods_discount;       //打折
    private double Goods_currentPrice;
    private int Goods_stock;
    private String Goods_size;
    private String Goods_introduction;
    private String Goods_bargain;    //是or否
    private String Goods_state;        //新旧程度
    private List<Comment> Goods_comment;         //评论
    private double Goods_score;             //评分
    private int Goods_flag;          //0 已下架 ，1已上架，-1已售罄
    private int Goods_volume;          //销量
    private int Goods_praise_count;
    private double Goods_praise_rate;
    private String Goods_uuid;
    private String Enter_uuid;
    private List<String> Goods_picture;
    private String Goods_firstP;
    private String Enter_name;
    private int Goods_count;
    private int Car_id;
}

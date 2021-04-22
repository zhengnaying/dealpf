package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Goods {
    private int Goods_id;
    private String Goods_name;
    private String Goods_type;
    private double Good_price;
    private int Goods_stock;
    private String Goods_size;
    private String Goods_introduction;
    private String Goods_bargain;
    private String Goods_photo;
    private String Goods_state;
    private String Goods_comment;
    private int Goods_flag;
}

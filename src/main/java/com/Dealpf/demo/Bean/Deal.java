package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Deal {
    private String Deal_uuid;
    private String Goods_uuid;
    private String Enter_uuid;
    private String User_uuid;
    private Timestamp Deal_time;
    private int Deal_count;
    private double Deal_assess;
    private int Deal_status;
    private double Deal_amount;
    private String Goods_name;
    private String Enter_name;
    private String User_name;
}

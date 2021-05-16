package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Shop_car {
    private int Car_id;
    private String Goods_uuid;
    private String User_uuid;
    private int Good_count;
    private int Good_status;
}

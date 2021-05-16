package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Data
public class Picture_temp {
    private int P_id;
    private String Goods_name;
    private String P_path;
    private Timestamp p_time;
}

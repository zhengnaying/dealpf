package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@ToString
@Data
public class Picture {
    private int P_id;
    private String P_path;
    private Timestamp P_time;
    private String Goods_uuid;
}

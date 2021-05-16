package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Transaction_system {
    private int Tran_id;
    private double Tran_amount;
    private String get_time;
    private String post_time;
    private int Tran_type;
    private String Enter_uuid;
    private String User_uuid;
    private String Deal_uuid;
    private String Enter_name;
    private String User_name;
}


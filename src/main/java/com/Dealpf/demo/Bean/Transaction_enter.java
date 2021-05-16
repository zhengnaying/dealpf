package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Transaction_enter {
    private int Tran_id;
    private double Tran_amount;
    private String Enter_uuid;
    private String Tran_arrive;
    private String Tran_start;
    private int Tran_type;
}

package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Wallet_user {
     private int Wallet_id;
     private int User_id;
     private int User_intergral;
     private double Current_money;
     private double Total_money;
}

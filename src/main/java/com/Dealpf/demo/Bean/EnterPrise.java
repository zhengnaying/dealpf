package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class EnterPrise {
    private int Enter_id;
    private String Enter_name;
    private String Enter_password;
    private String Enter_gender;
    private String Enter_account;
    private String Enter_phone;
    private String Enter_role;
    private String Enter_identity;
    private String Enter_license;
    private int Enter_grade;
    private int Enter_state;
    private String Enter_uuid;
    private int Enter_praise_count;
    private double Enter_praise_rate;
    private int Enter_deal_count;
    private double Enter_score;
    private List<Goods> Goods_List;
}

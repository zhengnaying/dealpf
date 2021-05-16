package com.Dealpf.demo.Bean;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Comment {
    private int Comment_id;
    private String Comment_content;
    private String User_uuid;
    private String Goods_uuid;
    private Timestamp Comment_time;
    private String Deal_uuid;
    private String user_Name;
}

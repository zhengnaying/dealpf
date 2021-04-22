package com.Dealpf.demo.Bean;


import lombok.Data;
import lombok.ToString;


import java.io.Serializable;

@ToString
@Data
public class User {
    private Long User_id;
    private String User_name;
    private String User_password;
    private String User_gender;
    private String User_city;
    private String User_account;
    private String User_phone;
    private String User_email;
    private String User_role;
    private int User_state;
}

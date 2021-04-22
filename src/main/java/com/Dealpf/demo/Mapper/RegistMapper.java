package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.Enterprice;
import com.Dealpf.demo.Bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RegistMapper {
    /**
     *用户注册方法
     */
    //注册成功添加用户
    @Insert("INSERT INTO User (User_name,User_password,User_gender,User_city,User_email,User_phone,User_account,User_role,User_state) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userCity},#{userEmail},#{userPhone},#{userAccount},#{userRole},#{userState});")
    void insertUser(String userName,String userPassword,String userGender,String userCity,String userEmail,
                    String userPhone,String userAccount,String userRole,int userState);

    //添加注册申请
    @Insert("INSERT INTO User_request (User_name,User_password,User_gender,User_city,User_email,User_phone,User_account,User_role) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userCity},#{userEmail},#{userPhone},#{userAccount},#{userRole});")
    void regist(String userName,String userPassword,String userGender,String userCity,String userEmail,
                String userPhone,String userAccount,String userRole);
    //查询用户注册申请表
    @Select("SELECT * FROM User_request WHERE User_name = #{userName} AND User_password= #{userPassword}")
    User getRequest(String userName, String userPassword);

    /**
     * 商家注册方法
     */
    @Insert("INSERT INTO User (User_name,User_password,User_gender,User_phone,User_account,User_role,User_identity,User_license) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userPhone},#{userAccount},#{userRole},#{userIdentity},#{userLicense});")
    void insertEnter(String userName,String userPassword,String userGender,
                    String userPhone,String userAccount,String userRole,String userIdentity,String userLicense);

    //商家添加注册申请
    @Insert("INSERT INTO User_request (User_name,User_password,User_gender,User_phone,User_account,User_role,User_identity,User_license) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userPhone},#{userAccount},#{userRole},#{userIdentity},#{userLicense});")
    void registEnter(String userName,String userPassword,String userGender,
                String userPhone,String userAccount,String userRole,String userIdentity,String userLicense);

    //查询商家注册申请表
    @Select("SELECT * FROM User_request WHERE User_name = #{userName} AND User_password= #{userPassword}")
    Enterprice getEnterRequest(String userName, String userPassword);

}

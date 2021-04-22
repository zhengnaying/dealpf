package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    //用姓名密码查询用户表
    @Select("SELECT * FROM User WHERE User_name = #{userName} AND User_password= #{userPassword}")
    User getUser(String userName, String userPassword);
}

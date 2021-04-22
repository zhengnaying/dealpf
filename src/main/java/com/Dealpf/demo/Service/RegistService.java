package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.Enterprice;
import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Mapper.RegistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistService {
    @Autowired
    RegistMapper registMapper;
    //注册成功添加用户
    public String insert(String userName, String userPassword, String userGender, String userCity, String userEmail, String userPhone, String userAccount, String userRole,int userState) {
        registMapper.insertUser(userName,userPassword,userGender,userCity,userEmail,userPhone,userAccount,userRole,userState);
        return "success!";
    }
   //用户提交注册申请
    public void regist(String userName, String userPassword, String userGender, String userCity, String userEmail, String userPhone, String userAccount,String userRole) {
        registMapper.regist(userName,userPassword,userGender,userCity,userEmail,userPhone,userAccount,userRole);
    }
    //注册成功添加商家
    public String insertEnter(String userName, String userPassword, String userGender, String userPhone, String userAccount, String userRole,String userIdentity,String userLicense) {
        registMapper.insertEnter(userName,userPassword,userGender,userPhone,userAccount,userRole,userIdentity,userLicense);
        return "success!";
    }
    //商家提交注册申请
    public void registEnter(String userName, String userPassword, String userGender, String userPhone, String userAccount, String userRole,String userIdentity,String userLicense) {
        registMapper.registEnter(userName,userPassword,userGender,userPhone,userAccount,userRole,userIdentity,userLicense);
    }
    //获取商家注册信息
    public Enterprice getEnterRequst(String userName, String userPassword) {
        return registMapper.getEnterRequest(userName, userPassword);
    }
    //获取用户注册信息
    public User getRequst(String userName, String userPassword) {
        return registMapper.getRequest(userName, userPassword);
    }
}

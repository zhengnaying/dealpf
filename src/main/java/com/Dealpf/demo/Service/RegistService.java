package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Mapper.RegistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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
    public String insertEnter(String enterName, String enterPassword, String enterGender, String enterPhone, String enterAccount, String enterRole, String enterIdentity, String enterLicense,int enterGrade,double enterPraiseRate,double enterPraiseCount,double enterDealCount,int enterState,double enterScore) {
        registMapper.insertEnter(enterName,enterPassword,enterGender,enterPhone,enterAccount,enterRole,enterIdentity,enterLicense,enterGrade,enterPraiseRate,enterPraiseCount,enterDealCount,enterState,enterScore);
        return "success!";
    }
    //商家提交注册申请
    public void registEnter(String enterName, String enterPassword, String enterGender, String enterPhone, String enterAccount, String enterRole,String enterIdentity,String enterLicense) {
        registMapper.registEnter(enterName,enterPassword,enterGender,enterPhone,enterAccount,enterRole,enterIdentity,enterLicense);
    }

    //获取用户注册信息
    public User getUserRequest(String userName) {
        return registMapper.getUserRequest(userName);
    }

    public User getUser(String userName){
        return registMapper.getUser(userName);
    }

    //获取商家注册信息
    public EnterPrise getEnterRequest(String enterName) {
        return registMapper.getEnterRequest(enterName);
    }

    public EnterPrise getEnter(String enterName){
        return registMapper.getEnter(enterName);
    }


    public void deletUserRequest(String userName){
         registMapper.deleteUser(userName);
    }

    public void deletEnterRequest(String enterName){
        registMapper.deleteEnter(enterName);
    }
    public void insertWalletUser(int userId,int userIntergral,double totalMoney,double currentMoney){
        this.registMapper.insertWalletUser(userId,userIntergral,totalMoney,currentMoney);
    }
    public void insertWalletEnter(String enterUuid,double walletAmount){
        this.registMapper.insertWalletEnter(enterUuid,walletAmount);
    }

}

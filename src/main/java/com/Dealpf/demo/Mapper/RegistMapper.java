package com.Dealpf.demo.Mapper;

import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface RegistMapper {
    /**
     *用户注册方法
     */
    //注册成功添加用户
    @Insert("INSERT INTO User (User_name,User_password,User_gender,User_city,User_email,User_phone,User_account,User_role,User_state,User_uuid) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userCity},#{userEmail},#{userPhone},#{userAccount},#{userRole},#{userState},#{userUuid});")
    @SelectKey(keyProperty = "userUuid", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    void insertUser(String userName,String userPassword,String userGender,String userCity,String userEmail,
                    String userPhone,String userAccount,String userRole,int userState);

    //用户添加注册申请
    @Insert("INSERT INTO User_request (User_name,User_password,User_gender,User_city,User_email,User_phone,User_account,User_role) VALUES (#{userName}, #{userPassword}," +
            "#{userGender},#{userCity},#{userEmail},#{userPhone},#{userAccount},#{userRole});")
    void regist(String userName,String userPassword,String userGender,String userCity,String userEmail,
                String userPhone,String userAccount,String userRole);
    //查询用户注册申请表
    @Select("SELECT * FROM User_request WHERE User_name = #{userName} ")
    User getUserRequest(String userName);

    //查询用户
    @Select("SELECT * FROM User WHERE User_name = #{userName}")
    User getUser(String userName);
    /**
     * 商家注册方法
     */
    @Insert("INSERT INTO EnterPrise (Enter_name,Enter_password,Enter_gender,Enter_phone,Enter_account,Enter_role,Enter_identity,Enter_license,Enter_grade,Enter_uuid,Enter_praise_rate,Enter_praise_count,Enter_deal_count,Enter_state,Enter_score) VALUES (#{enterName}, #{enterPassword}," +
            "#{enterGender},#{enterPhone},#{enterAccount},#{enterRole},#{enterIdentity},#{enterLicense},#{enterGrade},#{enterUuid},#{enterPraiseRate},#{enterPraiseCount},#{enterDealCount},#{enterState},#{enterScore});")
    @SelectKey(keyProperty = "enterUuid", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    void insertEnter(String enterName,String enterPassword,String enterGender,
                    String enterPhone,String enterAccount,String enterRole,String enterIdentity,String enterLicense,int enterGrade,double enterPraiseRate,double enterPraiseCount,double enterDealCount,int enterState,double enterScore);

    //商家添加注册申请
    @Insert("INSERT INTO Enter_request (Enter_name,Enter_password,Enter_gender,Enter_phone,Enter_account,Enter_role,Enter_identity,Enter_license) VALUES (#{enterName},#{enterPassword}," +
            "#{enterGender},#{enterPhone},#{enterAccount},#{enterRole},#{enterIdentity},#{enterLicense});")
    void registEnter(String enterName, String enterPassword, String enterGender,
                     String enterPhone, String enterAccount, String enterRole, String enterIdentity, String enterLicense);
    //查询商家注册申请表
    @Select("SELECT * FROM Enter_request WHERE Enter_name = #{enterName} ")
    EnterPrise getEnterRequest(String enterName);

    //查询商家
    @Select("SELECT * FROM EnterPrise WHERE Enter_name = #{enterName}")
    EnterPrise getEnter(String enterName);

    //删除用户申请记录
    @Delete("DELETE FROM User_request WHERE User_name = #{userName}")
    void deleteUser(String userName);

    //删除商家申请记录
    @Delete("DELETE FROM Enter_request WHERE Enter_name = #{enterName}")
    void deleteEnter(String enterName);
    @Insert("INSERT INTO Wallet_user (User_id,User_intergral,Total_money,Current_money) VALUES (#{userId},#{userIntergral}," +
            "#{totalMoney},#{currentMoney})")
    void insertWalletUser(int userId,int userIntergral,double totalMoney,double currentMoney);
    @Insert("INSERT INTO Wallet_enter (Enter_uuid,Wallet_amount) VALUES (#{enterUuid},#{walletAmount})")
    void insertWalletEnter(String enterUuid,double walletAmount);

}

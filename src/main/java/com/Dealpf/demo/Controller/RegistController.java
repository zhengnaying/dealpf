package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.Enterprice;
import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Service.RegistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("register")
public class RegistController {
    RegistService  registService;
    @Autowired
    public RegistController (RegistService registService){
        this.registService = registService;
    }

    /**
     *
     *  管理员审核后注册成功添加用户
     */
    @RequestMapping(value = "addSuccess",method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,@RequestParam("userGender") String userGender,
                         @RequestParam("userCity") String userCity,@RequestParam("userEmail") String userEmail,@RequestParam("userPhone") String userPhone,@RequestParam("userAccount") String userAcount){
        return registService.insert(userName,userPassword,userGender,userCity,userEmail,userPhone,userAcount,"普通用户",0);
    }

    /**
     * 用户申请注册
     */
    @RequestMapping(value = "regist",method = RequestMethod.POST)
    public String regist(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,@RequestParam("userGender") String userGender,
                         @RequestParam("userCity") String userCity,@RequestParam("userEmail") String userEmail,@RequestParam("userPhone") String userPhone,@RequestParam("userAccount") String userAcount){
        String flag = "error";
        User reqUser = registService.getRequst(userName,userPassword);
        HashMap<String,Object> res = new HashMap<>();
        if(reqUser == null) {
            flag = "success";
            registService.regist(userName,userPassword,userGender,userCity,userEmail,userPhone,userAcount,"普通用户");
            reqUser= registService.getRequst(userName,userPassword);
        }
        res.put("flag",flag);
        res.put("user",reqUser);
        //转成json字符串
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     *  管理员审核后注册成功添加商家
     */
    @RequestMapping(value = "addEnterSuccess",method = RequestMethod.POST)
    @ResponseBody
    public String insertEnter(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,@RequestParam("userGender") String userGender,
                         @RequestParam("userPhone") String userPhone,@RequestParam("userAccount") String userAcount,@RequestParam("userIndentity") String userIdentity,@RequestParam("userLicense") String userLicense){
        return registService.insertEnter(userName,userPassword,userGender,userPhone,userAcount,"商家",userIdentity,userLicense);
    }
    /**
     * 商家申请注册
     */
    @RequestMapping(value = "registEnter",method = RequestMethod.POST)
    @ResponseBody
    public String registEnter(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,@RequestParam("userGender") String userGender,
                         @RequestParam("userPhone") String userPhone,@RequestParam("userAccount") String userAcount,@RequestParam("userIndentity") String userIdentity,@RequestParam("userLicense") String userLicense){
        String flag = "error";
        Enterprice reqEnter = registService.getEnterRequst(userName,userPassword);
        HashMap<String,Object> res = new HashMap<>();
        if(reqEnter == null) {
            flag = "success";
            registService.registEnter(userName,userPassword,userGender,userPhone,userAcount,"商家",userIdentity,userLicense);
            reqEnter= registService.getEnterRequst(userName,userPassword);
        }
        res.put("flag",flag);
        res.put("user",reqEnter);
        //转成json字符串
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Service.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("login")
public class LoginController extends Exception{
    LoginService loginService;

    @Autowired
    public LoginController (LoginService loginService){
        this.loginService = loginService;
    }

    /**
     * 用户登录方法
     * @return json字符串
     */
    @RequestMapping(value="userLogin",method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword) {
        String flag="error";
        User user = loginService.getUser(userName,userPassword);
        HashMap<String,Object> res = new HashMap<>();
        if(user !=null && user.getUser_role().equals("普通用户")){
            flag="success";
            user.setUser_state(1);
        }
        res.put("flag",flag);
        res.put("user",user);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }

    /**
     * 管理员登录
     * @return json字符串
     */
    @RequestMapping("adminLogin")
    @ResponseBody
    public String adminLogin(@RequestParam("userName") String userName,@RequestParam("userPassword") String userPassword){
        String flag = "error";
        User admin = loginService.getUser(userName,userPassword);
        HashMap<String,Object> res = new HashMap<>();
        if(admin!=null && admin.getUser_role().equals("管理员")){
            flag = "success";
            admin.setUser_state(1);
            res.put("flag",flag);
            res.put("admin",admin);
        }
        else {
            res.put("flag",flag);
            res.put("admin","null");
        }
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }

    /**
     * 商家登录方法
     * @return json字符串
     */
    @RequestMapping(value="enterLogin",method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(@RequestParam("enterName") String enterName) {
        String flag="error";
        EnterPrise enter = loginService.getEnter(enterName);
        HashMap<String,Object> res = new HashMap<>();
        if(enter !=null && enter.getEnter_role().equals("商家")){
            flag="success";
            enter.setEnter_state(1);
        }
        res.put("flag",flag);
        res.put("enter",enter);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }

}

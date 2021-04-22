package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        if(user !=null && !user.getUser_role().equals("管理员")){
            flag="success";
            user.setUser_state(1);
        }
        res.put("flag",flag);
        res.put("user",user);
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
            res.put("flag",flag);
            res.put("admin",admin);
        }
        else {
            res.put("flag",flag);
            res.put("admin","null");
        }
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

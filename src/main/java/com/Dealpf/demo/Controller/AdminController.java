package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
   AdminService adminService;
    @Autowired
    public AdminController (AdminService adminService){
        this.adminService = adminService;
    }

    /**
     *   获取用户注册申请列表
     */
    @RequestMapping(value = "registUser",method = RequestMethod.GET)
    @ResponseBody
    public String getRequestUser(){
        List<User> registList = adminService.getRegistList();
        if(registList != null) {
            //转成json字符串
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(registList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取所有用户列表
     */
     @RequestMapping(value = "userList",method = RequestMethod.GET)
     @ResponseBody
    public String getUserList(){
         List<User> userList = adminService.getUserList();
         if(userList != null){
             //转成json字符串
             ObjectMapper mapper = new ObjectMapper();
             try {
                 return mapper.writeValueAsString(userList);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return null;
     }
    /**
     * 删除用户/商家
     */

    @RequestMapping(value = "deleteUser",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@RequestParam("userName") String userName,@RequestParam("userPassword") String userPassword){
      String flag="error";
    if(adminService.getUser(userName,userPassword) != null){
        adminService.delectUser(userName,userPassword);
        flag = "success";
    }
    return flag;
    }


    /**
     * 更新用户权限方法
     */
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestParam("userId") String userId,@RequestParam("userRole") String userRole){
        if(!adminService.getUserById(userId).getUser_role().equals(userRole)) {
            adminService.updateUser(userId, userRole);
            return "success";
        }
        else return "error";
    }
}

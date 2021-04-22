package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
   //获取用户列表
    public List<User> getUserList() {
        return adminMapper.getUserList();
    }
   //获取注册申请列表
    public List<User> getRegistList() {
        return adminMapper.getRegistList();
    }
    //查询指定用户
    public User getUser(String userName,String userPassword){
        return adminMapper.getUser(userName,userPassword);
    }
    public User getUserById(String userId){
        return adminMapper.getUserByID(userId);
    }
    //删除用户
    public void delectUser(String userName, String userPassword) {
        adminMapper.deleteUser(userName,userPassword);
    }

    //更新用户权限
    public void updateUser(String userId,String data){
        adminMapper.update(userId,data);
    }

}

package com.Dealpf.demo.Service;

import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginMapper loginMapper;
    //获取用户
    public User getUser(String userName, String userPassword) {
        return loginMapper.getUser(userName,userPassword);
    }
}

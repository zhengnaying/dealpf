package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    UserService userService;

    @Autowired
   public UserController (UserService userService){
       this.userService = userService;
   }






}

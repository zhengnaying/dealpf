package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.User;
import com.Dealpf.demo.Service.FileUploadService;
import com.Dealpf.demo.Service.RegistService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("register")
public class RegistController {
    RegistService  registService;
    private FileUploadService fileUploadService;
    @Autowired
    public RegistController (RegistService registService,FileUploadService fileUploadService){
        this.registService = registService;
        this.fileUploadService =fileUploadService;
    }
    public static HashMap<String,EnterPrise> enterList = new HashMap<>();

    /**
     *
     *  管理员审核后注册成功添加用户
     */
    @RequestMapping(value = "addSuccess",method = RequestMethod.POST)
    @ResponseBody
    public String insert(@RequestParam("userName") String userName){
        User newUser = registService.getUserRequest(userName);
            registService.insert(userName, newUser.getUser_password(), newUser.getUser_gender(), newUser.getUser_city(), newUser.getUser_email(), newUser.getUser_phone(), newUser.getUser_account(), "普通用户", 0);
            registService.deletUserRequest(userName);
            User user = this.registService.getUser(userName);
            registService.insertWalletUser(user.getUser_id(),0,0,0);
            return "success";
    }

    /**
     * 用户申请注册
     */
    @RequestMapping(value = "regist",method = RequestMethod.POST)
    public String regist(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword,@RequestParam("userGender") String userGender,
                         @RequestParam("userCity") String userCity,@RequestParam("userEmail") String userEmail,@RequestParam("userPhone") String userPhone,@RequestParam("userAccount") String userAccount){
        String flag = "error";
        User reqUser = registService.getUserRequest(userName);
        User user = registService.getUserRequest(userName);
        HashMap<String,Object> res = new HashMap<>();
        if(reqUser == null & user == null &userName.length()>4 & userName.length()<13) {
            flag = "success";
            registService.regist(userName,userPassword,userGender,userCity,userEmail,userPhone,userAccount,"普通用户");
            reqUser= registService.getUserRequest(userName);
        }
        res.put("flag",flag);
        res.put("user",reqUser);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }
    /**
     * 商家上传身份证，营业执照
     */
    @RequestMapping(value ="uploadEnter",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("enterName") String enterName, @RequestParam("enterIdentity") MultipartFile enterIdentity, @RequestParam("enterLicense")MultipartFile enterLicense) throws IOException {
      if(enterIdentity.isEmpty())
          return "身份证上传失败，请选择文件";
      if(enterLicense.isEmpty())
          return "营业执照上传失败，请选择文件";
        EnterPrise enter = new EnterPrise();
        enter.setEnter_name(enterName);
        String identity = this.fileUploadService.upload(enterIdentity);
        String license = this.fileUploadService.upload(enterLicense);
        enter.setEnter_identity(identity);
        enter.setEnter_license(license);
        if(enterList.containsKey(enterName)){
              enterList.get(enterName).setEnter_identity(identity);
              enterList.get(enterName).setEnter_license(license);
              return "更新成功";
          }
        else{
            enterList.put(enterName,enter);
            return "上传成功";
        }
    }

    /**
     * 商家注册申请
     */
    @RequestMapping(value = "registEnter",method = RequestMethod.POST)
    public String registEnter(@RequestParam("enterName") String enterName, @RequestParam("enterPassword") String enterPassword, @RequestParam("enterGender") String enterGender,
                              @RequestParam("enterPhone") String enterPhone, @RequestParam("enterAccount") String enterAccount){
        String flag = "error";
        EnterPrise enterRequest = registService.getEnterRequest(enterName);
        EnterPrise enter = registService.getEnter(enterName);
        HashMap<String,Object> res = new HashMap<>();
        if(enterRequest == null && enter == null) {
            flag = "success";
            String enterIdentity = enterList.get(enterName).getEnter_identity();
            String enterLicense = enterList.get(enterName).getEnter_license();
            registService.registEnter(enterName,enterPassword,enterGender,enterPhone,enterAccount,"商家",enterIdentity,enterLicense);
            enterList.remove(enterName);
            enterRequest= registService.getEnterRequest(enterName);
        }
        res.put("flag",flag);
        res.put("user",enterRequest);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }

    /**
     *  管理员审核后注册成功添加商家
     */
    @RequestMapping(value = "addEnterSuccess",method = RequestMethod.POST)
    @ResponseBody
    public String insertEnter(@RequestParam("enterName") String enterName){
        EnterPrise newEnter = registService.getEnterRequest(enterName);
        registService.insertEnter(enterName,newEnter.getEnter_password(),newEnter.getEnter_gender(),newEnter.getEnter_phone(),newEnter.getEnter_account(),"商家",newEnter.getEnter_identity(),newEnter.getEnter_license(),1,0,0,0,0,0);
        registService.deletEnterRequest(enterName);
        EnterPrise enter = registService.getEnter(enterName);
        registService.insertWalletEnter(enter.getEnter_uuid(),0);
        return "success";
    }

    /**
     * 用户注册未成功
     */
    @RequestMapping(value = "userAddFailed" ,method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam("userName") String userName){
        registService.deletUserRequest(userName);
        return "error";
    }
    /**
     *商家注册未成功
     */
    @RequestMapping(value = "enterAddFailed" ,method = RequestMethod.POST)
    @ResponseBody
    public String deleteEnter(@RequestParam("enterName") String enterName){
        registService.deletEnterRequest(enterName);
        return "error";
    }

}

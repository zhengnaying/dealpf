package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Service.AdminService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

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
    public List<User> getRequestUser(){
        List<User> registList = adminService.getUserRegist();
//        if(registList != null) {
//            //转成json字符串
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            return gson.toJson(registList);
//        }
        return registList;
    }
    /**
     *   获取商家注册申请列表
     */
    @RequestMapping(value = "registEnter",method = RequestMethod.GET)
    @ResponseBody
    public List<EnterPrise> getRequestEnter(){
        List<EnterPrise> registList = adminService.getEnterRegist();
//        if(registList != null) {
//            //转成json字符串
//            //转成json字符串
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            return gson.toJson(registList);
//        }
        return registList;
    }
    /**
     *   获取商品上传申请列表
     */
    @RequestMapping(value = "getGoodsRequest",method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> getGoodsRequest(){
        List<Goods> goodsRequestList = adminService.getGoodsRequest();
        for(Goods g: goodsRequestList){
         g.setEnter_name(this.adminService.getEnterName(g.getEnter_uuid()));
        }
//        if(goodsRequestList != null) {
//            //转成json字符串
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            return gson.toJson(goodsRequestList);
//        }
        return goodsRequestList;
    }
    /**
     * 获取商家申请照片
     */
    @RequestMapping(value = "getEnterRequestPhoto",method = RequestMethod.POST)
    @ResponseBody
    public String getEnterRequestPhoto(@RequestParam("enterName") String enterName){
        EnterPrise enter = adminService.getEnterRequestByName(enterName);
        HashMap<String,String> res = new HashMap<>();
        res.put("enterIdentity",enter.getEnter_identity());
        res.put("enterLicense",enter.getEnter_license());
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }
    /**
     * 获取商品申请照片
     */
    @RequestMapping(value = "getGoodsRequestPhoto",method = RequestMethod.POST)
    @ResponseBody
    public String getGoodsRequestPhoto(@RequestParam("goodsName") String goodsName){
        String flag ="error";
        ArrayList<Picture_temp> photo = this.adminService.getPictureTempByName(goodsName);
        ArrayList<String> url = new ArrayList<>();
        HashMap<String,Object> res = new HashMap<>();
        if(photo.size()>0) {
            flag ="success";
            for(Picture_temp p :photo){
                url.add(p.getP_path());
            }
        }
        res.put("flag",flag);
        res.put("photoList",url);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
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
             //转成json字符串
             GsonBuilder builder = new GsonBuilder();
             builder.setPrettyPrinting();
             Gson gson = builder.create();
             return gson.toJson(userList);
         }
         return null;
     }
    /**
     * 获取所有商家列表
     */
    @RequestMapping(value = "enterList",method = RequestMethod.GET)
    @ResponseBody
    public List<EnterPrise> getEnterList(){
        List<EnterPrise> enterList = adminService.getEnterList();

        return enterList;
    }
    /**
     * 获取所有商品列表
     */
    @RequestMapping(value = "goodsList",method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> getGoodsList(){
        List<Goods> goodsList = adminService.getGoodsList();
        for(Goods g: goodsList){
            g.setEnter_name(this.adminService.getEnterName(g.getEnter_uuid()));
        }
//        if(enterList != null){
//            //转成json字符串
//            GsonBuilder builder = new GsonBuilder();
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            return gson.toJson(enterList);
//        }
        return goodsList;
    }
    /**
     * 获取用户详细信息
     */
    @RequestMapping(value = "getUserInformation",method = RequestMethod.POST)
    @ResponseBody
    public String getUserInformation(@RequestParam ("userName") String userName){
        String flag = "error";
        User user = adminService.getUserByName(userName);
        HashMap<String,Object> res = new HashMap<>();
        if(user!= null){
            flag = "success";
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
     * 获取商家详细信息
     */
    @RequestMapping(value = "getEnterInformation",method = RequestMethod.POST)
    @ResponseBody
    public String getEnterInformation(@RequestParam ("enterName") String enterName){
        String flag = "error";
        EnterPrise enter = adminService.getEnterByName(enterName);
        HashMap<String,Object> res = new HashMap<>();
        if(enter!= null){
            flag = "success";
        }
        res.put("flag",flag);
        res.put("enter",enter);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }
    /**
     * 删除用户
     */
    @RequestMapping(value = "deleteUser",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@RequestParam("userName") String userName){
      String flag="error";
      User user = this.adminService.getUserByName(userName);
    if(user != null){
        adminService.deleteWalletUser(user.getUser_id());
        adminService.deleteUserTran(user.getUser_uuid());
        adminService.deleteShopCar(user.getUser_uuid());
        adminService.delectUser(userName);
        flag = "success";
    }
    return flag;
    }
    /**
     * 删除商家
     */
    @RequestMapping(value = "deleteEnter",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteEnter(@RequestParam("enterName") String enterName){
        String flag="error";
        EnterPrise enter = this.adminService.getEnterByName(enterName);
        if(enter != null){
            adminService.deleteWalletEnter(enter.getEnter_uuid());
            adminService.deleteTransactionEnter(enter.getEnter_uuid());
            List<Goods> goodsRequest = adminService.getEnterGoodsRequest(enter.getEnter_uuid());
            for(Goods g:goodsRequest){
                adminService.deletePictureTemp(g.getGoods_name());
                adminService.deleteGoodsRequest(g.getGoods_name());
            }
            List<Goods> goods = adminService.getEnterGoods(enter.getEnter_uuid());
            for(Goods g:goods){
                adminService.deletePicture(g.getGoods_uuid());
                adminService.deleteComment(g.getGoods_uuid());
                adminService.deleteShopCarGoods(g.getGoods_uuid());
                adminService.deleteGoods(g.getGoods_uuid());
            }
            adminService.delectEnter(enterName);
            flag = "success";
        }
        return flag;
    }

    /**
     * 更新用户权限方法
     */
    @RequestMapping(value = "updateUser",method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestParam("userName") String userName,@RequestParam("userRole") String userRole){
        if(!adminService.getUserByName(userName).getUser_role().equals(userRole)) {
            adminService.updateUser(userName, userRole);
            return "success";
        }
        else return "error";
    }

    /**
     * 用户名模糊查询用户方法
     */
    @RequestMapping(value = "getUserByName",method = RequestMethod.POST)
    @ResponseBody
    public String getUserByName(@RequestParam("userName") String userName){
        if(userName != null){
            List<User> userList = adminService.getUserListByName("%"+userName+"%");
            if(userList != null) {
                //转成json字符串
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                return gson.toJson(userList);
            }
        }
        return null;
    }
    /**
     * 商家名模糊查询用户方法
     */
    @RequestMapping(value = "getEnterByName",method = RequestMethod.POST)
    @ResponseBody
    public String getEnterByName(@RequestParam("enterName") String enterName){
        if(enterName != null){
            List<EnterPrise> enterList = adminService.getEnterListByName("%"+enterName+"%");
            if(enterList != null) {
                //转成json字符串
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();
                return gson.toJson(enterList);
            }
        }
        return null;
    }
    /**
     * 更改商家等级
     */
    @RequestMapping(value = "upgrade",method = RequestMethod.POST)
    @ResponseBody
    public String upgrade(@RequestParam("enterName") String enterName,@RequestParam("enterGrade") int enterGrade){
        if(enterName!= null && enterGrade>0 && enterGrade <6){
            this.adminService.updateGrade(enterName,enterGrade);
            return "success";
        }
        return "error";
    }
    /**
     * 同意上传商品
     */
    @RequestMapping(value = "insertGoods",method = RequestMethod.POST)
    @ResponseBody
    public String insertGoods(@RequestParam("goodsName") String goodsName){
        Goods goods = adminService.getGoodsRequestByName(goodsName);
        if(goods ==null){
            return "error";
        }
        double goodsCurrentPrice = goods.getGoods_price()*goods.getGoods_discount()/10;
        adminService.insertGoods(goodsName,goods.getGoods_type(),goods.getGoods_price(),goods.getGoods_size(),goods.getGoods_stock(),goods.getGoods_introduction(),
                goods.getGoods_bargain(),goods.getGoods_state(),0,1,0,0,goods.getGoods_discount(),0,goods.getEnter_uuid(),goodsCurrentPrice);
        ArrayList<String> url = new ArrayList<>();
        for(Picture_temp p : adminService.getPictureTempByName(goodsName)){
            url.add(p.getP_path());
        }
        for(String i :url){
            adminService.insertPicture(i,adminService.getGoodsByName(goodsName).getGoods_uuid());
        }
        adminService.deletePictureTemp(goodsName);
        adminService.deleteGoodsRequest(goodsName);
        return "success";
    }

    /**
     * 拒绝上传商品
     */
    @RequestMapping(value = "insertGoodsFailed",method = RequestMethod.POST)
    @ResponseBody
    public String insertGoodsFailed(@RequestParam("goodsName") String goodsName){
        Goods goods = adminService.getGoodsRequestByName(goodsName);
        if(goods == null)
            return "error";
        adminService.deletePictureTemp(goodsName);
        adminService.deleteGoodsRequest(goodsName);
        return "success";
    }

/**
 * 给用户充钱
 */
@RequestMapping(value = "setCurrentMoney",method = RequestMethod.POST)
@ResponseBody
public String setCurrentMoney(@RequestParam("currentMoney") double money,@RequestParam("userId")int userId){
    if(money <=0)
        return "error";
    this.adminService.setCurrentMoney(this.adminService.getCurrent_money(userId)+money,userId);
    return "success";
}
/**
 * 获取交易明细
 */
    @RequestMapping(value = "getSystemTran",method = RequestMethod.GET)
    @ResponseBody
    public List<Transaction_system> getSystemTran(){

        List<Transaction_system> tran_sys = this.adminService.getSystemTran();
        for(Transaction_system  t :tran_sys){
            Deal d = this.adminService.getDeal(t.getDeal_uuid());
            t.setEnter_name(this.adminService.getEnterByUuid(d.getEnter_uuid()).getEnter_name());
            t.setUser_name(this.adminService.getUserByUuid(d.getUser_uuid()).getUser_name());
        }
        return tran_sys;
    }
    /**
     * 获取钱包
     */
    @RequestMapping(value = "getWalletSystem",method = RequestMethod.GET)
    @ResponseBody
    public Wallet_system getWalletSystem(){
        return this.adminService.getWalletSystem();
    }

}

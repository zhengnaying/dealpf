package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Service.EnterService;
import com.Dealpf.demo.Service.FileUploadService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("enter")
public class EnterController {
    EnterService enterService;
    FileUploadService fileUploadService;
    @Autowired
    public EnterController(EnterService enterService,FileUploadService fileUploadService){
        this.enterService = enterService;
        this.fileUploadService = fileUploadService;
    }
  public static HashMap<String, Goods> goodsList = new HashMap<>();

    public static HashMap<String, ArrayList<String>> photo = new HashMap<>();
    /**
     *上传商品照片
     */
    @RequestMapping(value ="uploadGoods",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("goodsName") String goodsName,@RequestParam("goodsPhoto") MultipartFile[] goodsPhoto) throws IOException {
        ArrayList<String> photoList = new ArrayList<>();
        if ( goodsPhoto != null &&  goodsPhoto.length > 0) {
            for (MultipartFile file : goodsPhoto) {
                photoList.add(this.fileUploadService.upload(file));
            }
            if(photo.containsKey(goodsName))
                photo.get(goodsName).clear();
            photo.put(goodsName,photoList);
        }
            return "上传成功";
    }
    /**
     * 商品上传商品
     */
    @RequestMapping(value = "goodsRequest",method = RequestMethod.POST)
    @ResponseBody
    public String goodsRequest(@RequestParam("goodsName") String goodsName, @RequestParam("goodsType") String goodsType,@RequestParam("goodsPrice") double goodsPrice,@RequestParam("goodsSize") String goodsSize,@RequestParam("goodsStock") int goodsStock,@RequestParam("goodsIntroduction") String goodsIntroduction,@RequestParam("goodsBargain") String goodsBargain,
                               @RequestParam("goodsState") String goodsState,@RequestParam("goodsDiscount") double goodsDiscount,@RequestParam("enterUuid") String enterUuid){
       String flag = "error";
       Goods goodRequest =  enterService.searchGoodsRequest(goodsName);
       Goods good = enterService.getGood(goodsName);
       HashMap<String,Object> res = new HashMap<>();
        if(good==null && goodRequest == null){
           flag = "success";
           ArrayList<String> photoList = photo.get(goodsName);
           ArrayList<Picture_temp> p = this.enterService.getPicture_temp(goodsName);
           if(p.size()>0){
               this.enterService.deletePictureTemp(goodsName);
           }
           this.enterService.goodsRequest(goodsName,goodsType,goodsPrice,goodsSize,goodsStock,goodsIntroduction,goodsBargain,goodsState,goodsDiscount,enterUuid);
            for(String s : photoList)
                this.enterService.insertPictureTemp(goodsName,s);
           photo.remove(goodsName);
           goodsList.remove(goodsName);
           goodRequest = enterService.searchGoodsRequest(goodsName);
       }
        res.put("flag",flag);
        res.put("good",goodRequest);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }

    /**
     * 下架商品
     */
    @RequestMapping(value = "dropGoods",method = RequestMethod.POST)
    @ResponseBody
    public String dropGoods(@RequestParam("goodsName")String goodsName){
        Goods goods = this.enterService.getGood(goodsName);
        if(goods == null)
            return "error";
        this.enterService.dropGoods(goodsName);
        return "success";
    }

    /**
     * 获取已上架商品列表
     */
    @RequestMapping(value = "onSaleGoods",method = RequestMethod.POST)
    @ResponseBody
    public String getOnSaleGoods(@RequestParam("enterUuid")String enterUuid){
        String flag = "error";
        List<Goods> goods = this.enterService.getOnSaleGoods(enterUuid);
        HashMap<String,Object> res = new HashMap<>();
        if(goods != null && goods.size()>0){
            flag = "success";
        }
        res.put("flag",flag);
        res.put("goodsList",goods);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }
    /**
     * 获取已下架商品列表
     */
    @RequestMapping(value = "droppedGoods",method = RequestMethod.POST)
    @ResponseBody
    public String getdroppedGoods(@RequestParam("enterUuid")String enterUuid){
        String flag = "error";
        List<Goods> goods = this.enterService.getDroppedGoods(enterUuid);
        HashMap<String,Object> res = new HashMap<>();
        if(goods != null && goods.size()>0){
            flag = "success";
        }
        res.put("flag",flag);
        res.put("goodsList",goods);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(res);
    }
    /**
     * 获取商家钱包
     */
    @RequestMapping(value = "getWalletEnter", method = RequestMethod.POST)
    @ResponseBody
    public Wallet_enter getWalletUser(@RequestParam("enterUuid") String enterUuid){
        if(enterUuid == null)
            return null;
        Wallet_enter we = this.enterService.getWalletEnter(enterUuid);
        return we;
    }
    /**
     * 获取商家钱包明细
     */
    @RequestMapping(value = "getTransactionEnter", method = RequestMethod.POST)
    @ResponseBody
    public List<Transaction_enter> getTransactionEnter(@RequestParam("enterUuid")String enterUuid){
        if(enterUuid == null)
            return null;
        return this.enterService.getTransactionEnter(enterUuid);
    }
    /**
     * 获取退款申请列表
     */
    @RequestMapping(value = "getRefundRequest", method = RequestMethod.POST)
    @ResponseBody
    public List<Deal> getRefundRequest(@RequestParam("enterUuid")String enterUuid){
        List<Deal> deal = this.enterService.getRefundRequest(enterUuid);
        for(Deal d :deal){
            d.setEnter_name(this.enterService.getEnter(d.getEnter_uuid()).getEnter_name());
            d.setGoods_name(this.enterService.getGoodByUuid(d.getGoods_uuid()).getGoods_name());
            d.setUser_name(this.enterService.getUserByUuid(d.getUser_uuid()).getUser_name());
        }
        return deal;
    }

}

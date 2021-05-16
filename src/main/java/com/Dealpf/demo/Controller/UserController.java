package com.Dealpf.demo.Controller;

import com.Dealpf.demo.Bean.*;
import com.Dealpf.demo.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    UserService userService;

    @Autowired
   public UserController (UserService userService){
       this.userService = userService;
   }

    /**
     * 添加购物车
     */
    @RequestMapping(value = "insertCar", method = RequestMethod.POST)
    @ResponseBody
    public String insertCar(@RequestParam("goodsUuid")String goodsUuid,@RequestParam("userUuid")String userUuid,
                            @RequestParam("goodCount")int goodCount){
        Goods good = this.userService.getGood(goodsUuid);
        User user = this.userService.getUserByUuid(userUuid);
        if(good == null || user == null)
            return "error";
        if(goodCount> good.getGoods_stock())
            return "error1";
        Shop_car car = this.userService.getCarByGoods(goodsUuid);
        if(car != null){
            this.userService.updateCar(goodsUuid,car.getGood_count()+goodCount);
        }
        else
            this.userService.insertCar(goodsUuid,userUuid,goodCount,0);
        return "success";
    }
    /**
     * 获取购物车列表
     */
    @RequestMapping(value = "getCar", method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> insertCar(@RequestParam("userUuid")String userUuid){
        List<Shop_car> list = this.userService.getCar(userUuid);
        if(list.size()==0)
            return null;
        List<Goods> goods = new ArrayList<>();
        for(Shop_car c :list)
            goods.add(this.userService.getGood(c.getGoods_uuid()));
        for(int i=0;i<goods.size();i++) {
            goods.get(i).setGoods_firstP(this.userService.getPictureByUuid(goods.get(i).getGoods_uuid()).get(0));
            goods.get(i).setEnter_name(this.userService.getEnterByUuid(goods.get(i).getEnter_uuid()).getEnter_name());
            goods.get(i).setGoods_count(list.get(i).getGood_count());
            goods.get(i).setCar_id(list.get(i).getCar_id());
        }
        return goods;
    }
    /**
     * 购物车修改商品数量
     */
    @RequestMapping(value = "setGoodCount", method = RequestMethod.POST)
    @ResponseBody
    public String setGoodCount(@RequestParam("goodCount") int goodCount,@RequestParam("carId")int carId){
        if(goodCount == 0) {
            this.userService.deleteCar(carId);
            return "success";
        }
        this.userService.setGoodCount(goodCount,carId);
        return "success";
    }
    /**
     * 购物车删除商品
     */
    @RequestMapping(value = "deleteCar", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCar(@RequestParam("carId")int carId){
        this.userService.deleteCar(carId);
        return "success";
    }
    /**
     * 用户购买商品(立即下单）
     */
    @RequestMapping(value = "placeOrder1", method = RequestMethod.POST)
    @ResponseBody
    public String placeOrder1(@RequestParam("goodsUuid")String goodsUuid,@RequestParam("userUuid")String userUuid,
                                @RequestParam("dealCount")int dealCount) throws InterruptedException {
        Goods goods = this.userService.getGood(goodsUuid);
        User user = this.userService.getUserByUuid(userUuid);
        if(goods == null||user == null)
            return "error1";
            if (dealCount > goods.getGoods_stock()||goods.getGoods_stock()==0)
                return "error2";
            double amount =dealCount * goods.getGoods_currentPrice();
            if(amount>this.userService.getWalletUser(user.getUser_id()).getCurrent_money())
                return "error3";
            this.userService.insertDeal(goodsUuid, userUuid,goods.getEnter_uuid(), dealCount, amount , 0);
            this.userService.updateGoodsStock(goodsUuid,goods.getGoods_stock()-dealCount);
            if(this.userService.getGood(goodsUuid).getGoods_stock()==0)
                this.userService.updateGoodsFlag(goodsUuid,0);
            this.userService.setCurrentMoney(this.userService.getWalletUser(user.getUser_id()).getCurrent_money()-amount,user.getUser_id());
            this.userService.setUserIntergral(this.userService.getWalletUser(user.getUser_id()).getUser_intergral()+(int)amount,user.getUser_id());
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String stratTime = dateFormat.format(System.currentTimeMillis());
           Deal deal = this.userService.getDealLast(1).get(0);
           this.userService.addSystemTran(deal.getDeal_uuid(),stratTime,null,amount,1);
           this.userService.setWalletSystem(this.userService.getSystemAmount()+amount);
            return "success";
    }
    /**
     * 收款
     */
    @RequestMapping(value = "getMoney", method = RequestMethod.POST)
    @ResponseBody
    public String getWalletAmount(@RequestParam("dealUuid")String dealUuid) throws InterruptedException {
        Deal deal = this.userService.getDeal(dealUuid);
        if(deal.getDeal_status() == 1)
            return  "error1";
        if(deal.getDeal_status() == -1)
            return  "error2";
        if(deal.getDeal_status() == 2)
            return  "error3";
        EnterPrise enter = this.userService.getEnterByUuid(deal.getEnter_uuid());
        Goods goods = this.userService.getGood(deal.getGoods_uuid());
        double amount =deal.getDeal_count() * goods.getGoods_currentPrice();
        double fee = this.calculateFee(enter.getEnter_grade(),amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stratTime = dateFormat.format(System.currentTimeMillis());
        try{
//            Thread.sleep(86400000);
            Thread.sleep(1000);
            String arriveTime = dateFormat.format(System.currentTimeMillis());
            this.userService.addSystemTran(dealUuid,null,arriveTime,amount-fee,0);
            this.userService.setWalletSystem(this.userService.getSystemAmount()-amount+fee);
            this.userService.createTrasaction(goods.getEnter_uuid(),stratTime,arriveTime,amount-fee,1);
            this.userService.setWalletAmount(this.userService.getWalletEnter(goods.getEnter_uuid()).getWallet_amount()+amount,goods.getEnter_uuid());
            this.userService.setDealStatus(dealUuid,1);
            return "arrived";
        }catch (Exception e){
            return "error";
        }
    }
    /**
     * 购物车一键购买
     */
    @RequestMapping(value = "placeOrder2", method = RequestMethod.POST)
    @ResponseBody
    public String placeOrder2(@RequestParam("userUuid")String userUuid){
        HashMap<String,String> res = new HashMap<>();
        String flag = "error";
       List<Shop_car> car = this.userService.getCar(userUuid);
        User user = this.userService.getUserByUuid(userUuid);
       if(car == null) {
           return flag;
       }
        List<Goods> goods = new ArrayList<>();
        List<Integer> dealcount = new ArrayList<>();
       for(Shop_car c : car) {
           goods.add(this.userService.getGood(c.getGoods_uuid()));
           dealcount.add(c.getGood_count());
       }
       for(int i=0;i<goods.size();i++){
           if(dealcount.get(i)>goods.get(i).getGoods_stock()||goods.get(i).getGoods_stock()==0){
               flag=goods.get(i).getGoods_name();
               res.put(flag,"error2");
               continue;
           }
           double amount = dealcount.get(i) * goods.get(i).getGoods_currentPrice();
           if(amount > this.userService.getWalletUser(user.getUser_id()).getCurrent_money()) {
               flag=goods.get(i).getGoods_name();
               res.put(flag,"error3");
               break;
           }
           flag=goods.get(i).getGoods_name();
           res.put(flag,"success");
           this.userService.insertDeal(goods.get(i).getGoods_uuid(), userUuid,goods.get(i).getEnter_uuid(), dealcount.get(i), amount , 0);
           this.userService.setCurrentMoney(this.userService.getWalletUser(user.getUser_id()).getCurrent_money()-amount,user.getUser_id());
           this.userService.updateGoodsStock(goods.get(i).getGoods_uuid(),goods.get(i).getGoods_stock()-dealcount.get(i));
           int currentStock=this.userService.getGood(goods.get(i).getGoods_uuid()).getGoods_stock();
           if(currentStock== 0)
               this.userService.updateGoodsFlag(goods.get(i).getGoods_uuid(),0);
           this.userService.setUserIntergral(this.userService.getWalletUser(user.getUser_id()).getUser_intergral()+(int)amount,user.getUser_id());
           this.userService.deleteCar(car.get(i).getCar_id());
       }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stratTime = dateFormat.format(System.currentTimeMillis());
        List<Deal> deal = this.userService.getDealLast(goods.size());
        for(Deal d:deal) {
            this.userService.addSystemTran(d.getDeal_uuid(), stratTime, null, d.getDeal_amount(), 1);
            this.userService.setWalletSystem(this.userService.getSystemAmount() + d.getDeal_amount());
        }
       // 转成json字符串
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            return gson.toJson(res);
    }
    /**
     * 购物车单个购买
     */
    @RequestMapping(value = "placeOrder3", method = RequestMethod.POST)
    @ResponseBody
    public String placeOrder3(@RequestParam("carId")int carId){
        Shop_car car = this.userService.getCarById(carId);
        if(car == null)
            return "error";
        User user = this.userService.getUserByUuid(car.getUser_uuid());
        Goods goods = this.userService.getGood(car.getGoods_uuid());
        if(car.getGood_count()>goods.getGoods_stock()||goods.getGoods_stock()==0)
            return "error2";
        double amount = car.getGood_count()*goods.getGoods_currentPrice();
        if(amount> this.userService.getWalletUser(user.getUser_id()).getCurrent_money())
            return "error3";
        this.userService.insertDeal(goods.getGoods_uuid(), user.getUser_uuid(),goods.getEnter_uuid(),car.getGood_count(), amount , 0);
        this.userService.updateGoodsStock(goods.getGoods_uuid(),goods.getGoods_stock()-car.getGood_count());
        if(this.userService.getGood(car.getGoods_uuid()).getGoods_flag()==0)
            this.userService.updateGoodsFlag(car.getGoods_uuid(),0);
        this.userService.setCurrentMoney(this.userService.getWalletUser(user.getUser_id()).getCurrent_money()-amount,user.getUser_id());
        this.userService.setUserIntergral(this.userService.getWalletUser(user.getUser_id()).getUser_intergral()+(int)amount,user.getUser_id());
        this.userService.deleteCar(car.getCar_id());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stratTime = dateFormat.format(System.currentTimeMillis());
        Deal deal = this.userService.getDealLast(1).get(0);
        this.userService.addSystemTran(deal.getDeal_uuid(),stratTime,null,amount,1);
        this.userService.setWalletSystem(this.userService.getSystemAmount()+amount);
        return "success";
    }

    /**
     * 用户评分、评论商品、商家,销量+1；
     */
    @RequestMapping(value = "assessGoods", method = RequestMethod.POST)
    @ResponseBody
    public String assessGoods(@RequestParam("dealUuid")String dealUuid,@RequestParam("goodsScore") double goodsScore,
                              @RequestParam("commentContent")String commentContent,@RequestParam("enterScore") double enterScore){
           Deal deal = this.userService.getDeal(dealUuid);
           if(deal.getDeal_status() != 1){
               return "error";
           }
           String goodsUuid = deal.getGoods_uuid();
           String enterUuid = deal.getEnter_uuid();
           Goods goods = this.userService.getGood(goodsUuid);
           EnterPrise enterPrise = this.userService.getEnterByUuid(enterUuid);
           if(goods == null || goodsScore >5  || goodsScore <=0 )
               return "error";
           if(enterPrise == null || enterScore >5  || enterScore <=0 )
               return "error";
           int deal_count = deal.getDeal_count();
           this.userService.setGoodsVolume(goodsUuid,goods.getGoods_volume()+deal_count);
           this.userService.updateDealAssess(deal.getDeal_uuid(),goodsScore);
           //商品评分计算
           if(goods.getGoods_score()==0 || goods.getGoods_volume() ==0)
               this.userService.assessGoods(goodsUuid,goodsScore);
           else{
               double score = goods.getGoods_score()*(goods.getGoods_volume());
               this.userService.assessGoods(goodsUuid,(score+goodsScore*deal_count)/(goods.getGoods_volume()+deal_count));
           }
           if(goodsScore>=3 && goodsScore <=5){
               this.userService.setGoodsPraiseCount(goodsUuid,goods.getGoods_praise_count()+deal_count);
               double goodsCurrentRate = 100*(goods.getGoods_praise_count()+deal_count)/(goods.getGoods_volume()+deal_count);
               this.userService.updateGoodsPraiseRate(goodsUuid,goodsCurrentRate);
           }else{
               double goodsCurrentRate =100*goods.getGoods_praise_count()/(goods.getGoods_volume()+deal_count);
               this.userService.updateGoodsPraiseRate(goodsUuid,goodsCurrentRate);
           }
           //添加商品评论
           Comment c = this.userService.getCommentByDeal(deal.getDeal_uuid());
           if(c != null)
               this.userService.updateComment(deal.getDeal_uuid(),commentContent);
           else
               this.userService.insertComment(commentContent,this.userService.getDeal(deal.getDeal_uuid()).getUser_uuid(),goodsUuid,deal.getDeal_uuid());
           //商家评分计算
           this.userService.setEnterDealCount(enterUuid,enterPrise.getEnter_deal_count()+1);
           if(enterPrise.getEnter_score()==0 || enterPrise.getEnter_deal_count() ==0)
               this.userService.assessEnter(enterUuid,enterScore);
           else{
               double score = enterPrise.getEnter_score()*(enterPrise.getEnter_deal_count());
               this.userService.assessEnter(enterUuid,(score+enterScore)/(enterPrise.getEnter_deal_count()+1));
           }
           if(enterScore>=3 && enterScore <=5){
               this.userService.setEnterPraiseCount(enterUuid,enterPrise.getEnter_praise_count()+1);
               double enterCurrentRate = 100*(enterPrise.getEnter_praise_count()+1)/(enterPrise.getEnter_deal_count()+1);
               this.userService.updateEnterPraiseRate(enterUuid,enterCurrentRate);
           }else{
               double enterCurrentRate =100*enterPrise.getEnter_praise_count()/(enterPrise.getEnter_deal_count()+1);
               this.userService.updateEnterPraiseRate(enterUuid,enterCurrentRate);
           }
        return "success";
    }
    /**
     * 获取用户钱包
     */
    @RequestMapping(value = "getWalletUser", method = RequestMethod.POST)
    @ResponseBody
    public Wallet_user getWalletUser(@RequestParam("userId") int userId){
        User user = this.userService.getUser(userId);
        if(user == null)
            return null;
        return this.userService.getWalletUser(userId);
    }
    /**
     * 获取用户所有订单
     */
    @RequestMapping(value = "getUserDeal", method = RequestMethod.POST)
    @ResponseBody
    public List<Deal> getUserDeal(@RequestParam("userUuid") String userUuid){
        User user = this.userService.getUserByUuid(userUuid);
        if(user == null)
            return null;
        List<Deal> dealList = this.userService.getUserDeal(userUuid);
        for(Deal d :dealList){
            Goods goods = this.userService.getGood(d.getGoods_uuid());
            d.setGoods_name(goods.getGoods_name());
            d.setEnter_name(this.userService.getEnterByUuid(d.getEnter_uuid()).getEnter_name());
        }
        return dealList;
    }

    /**
     * 申请退款
     */
    @RequestMapping(value = "requestRefund", method = RequestMethod.POST)
    @ResponseBody
    public String requestRefund(@RequestParam("dealUuid") String dealUuid){
        if(dealUuid == null)
            return "error";
        Deal deal = this.userService.getDeal(dealUuid);
        Timestamp dealTime = deal.getDeal_time();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(now.getTime()-dealTime.getTime() >=86400000){
            return "error1";
        }
        if(deal.getDeal_status() ==3 ||deal.getDeal_status() ==1 ||deal.getDeal_status()==-1)
            return "error2";
        if(deal.getDeal_status() ==2)
            return  "error3";
        this.userService.setDealStatus(dealUuid,2);
        return "success";
    }
    /**
    * 同意退款
    */
    @RequestMapping(value = "refund", method = RequestMethod.POST)
    @ResponseBody
    public String refund(@RequestParam("dealUuid") String dealUuid) throws InterruptedException {
        if(dealUuid == null)
            return "error";
        this.userService.setDealStatus(dealUuid,-1);
        Deal deal = this.userService.getDeal(dealUuid);
//        if(deal.getDeal_status() != -1)
//            return "error1";
        User user = this.userService.getUserByUuid(deal.getUser_uuid());
        EnterPrise enter = this.userService.getEnterByUuid(deal.getEnter_uuid());
//        this.userService.setWalletAmount(this.userService.getWalletEnter(enter.getEnter_uuid()).getWallet_amount()-deal.getDeal_amount(),enter.getEnter_uuid());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stratTime = dateFormat.format(System.currentTimeMillis());
        Thread.sleep(10000);
        String arrivedTime = dateFormat.format(System.currentTimeMillis());
//        this.userService.createTrasaction(enter.getEnter_uuid(),stratTime,arrivedTime,deal.getDeal_amount(),0);
        this.userService.setCurrentMoney(this.userService.getWalletUser(user.getUser_id()).getCurrent_money()+deal.getDeal_amount(),user.getUser_id());
        this.userService.setUserIntergral(this.userService.getWalletUser(user.getUser_id()).getUser_intergral()-(int)deal.getDeal_amount(),user.getUser_id());
        return "success";
    }

    /**
     * 拒绝退款
     */
    @RequestMapping(value = "rejectRefund", method = RequestMethod.POST)
    @ResponseBody
    public String rejectRefund(@RequestParam("dealUuid") String dealUuid){
        if(dealUuid == null)
            return "error";
        this.userService.setDealStatus(dealUuid,3);
        return "success";
    }
    public double calculateFee(int grade,double amount){
        if(grade == 1)
            return amount*0.1*0.01;
        if(grade == 2)
            return amount*0.2*0.01;
        if(grade == 3)
            return amount*0.05*0.01;
        if(grade ==4)
            return amount*0.75*0.01;
        else
            return amount*0.01;
    }
}

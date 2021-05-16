package com.Dealpf.demo.Controller;


import com.Dealpf.demo.Bean.Comment;
import com.Dealpf.demo.Bean.EnterPrise;
import com.Dealpf.demo.Bean.Goods;
import com.Dealpf.demo.Bean.Picture;
import com.Dealpf.demo.Service.ShopService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("shop")
public class ShopController {
    private final ShopService shopService;
    @Autowired
    public ShopController(ShopService shopService){
        this.shopService =shopService;
    }
    /**
     * 获取所有在售商品
     */
    @RequestMapping(value = "getGoodsOnSale",method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> getGoodsOnSale(){
        List<Goods> goods = this.shopService.getOnSaleGoods();
        if(goods ==null)
            return null;
        for(Goods g :goods){
            g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
            g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
        }

        return goods;
    }

    /**
     * 获取商品详情
     */
    @RequestMapping(value = "getGoodsDetail",method = RequestMethod.POST)
    @ResponseBody
    public String getGoodsDetail(@RequestParam("goodsName")String goodsName){
        Goods goods = this.shopService.getGoodsDetail(goodsName);
        if(goods ==null)
            return null;
        goods.setEnter_name(this.shopService.getEnterByUuid(goods.getEnter_uuid()).getEnter_name());
        goods.setGoods_picture(this.shopService.getPicture(goods.getGoods_uuid()));
        List<Comment> comments = this.shopService.getGoodsComment(goods.getGoods_uuid());
        for(Comment c: comments)
            c.setUser_Name(this.shopService.getUser(c.getUser_uuid()).getUser_name());
        goods.setGoods_comment(comments);
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(goods);
    }

    /**
     * 获取所有商家名列表
     */
    @RequestMapping(value = "getEnterName",method = RequestMethod.GET)
    @ResponseBody
    public String getEnterName(){
        List<String> enter = this.shopService.getEnterName();
        if(enter == null)
            return null;
        //转成json字符串
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(enter);
    }

    /**
     * 商家维度获取在售商品列表
     */
    @RequestMapping(value = "getGoodsOnSaleByShop",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, List<Goods>> getGoodsOnSaleByShop(){
        List<Goods> goods = this.shopService.getOnSaleGoods();
        if(goods ==null)
            return null;
        HashMap<String,List<Goods>> res = new HashMap<>();
        for(Goods g :goods){
            EnterPrise enter = this.shopService.getEnterByUuid(g.getEnter_uuid());
            String enterName = enter.getEnter_name();
            g.setGoods_picture(this.shopService.getPicture(g.getGoods_uuid()));
            if(res.containsKey(enterName))
                res.get(enterName).add(g);
            else {
                List<Goods> entergoods = new ArrayList<>();
                entergoods.add(g);
                res.put(enterName, entergoods);
            }
        }
        return res;
    }

    /**
     * 商家名模糊查询方法
     */
    @RequestMapping(value = "getEnterByName",method = RequestMethod.POST)
    @ResponseBody
    public List<EnterPrise> getEnterByName(@RequestParam("enterName") String enterName){
        if(enterName != null){
            List<EnterPrise> enterList = shopService.getEnterListByName("%"+enterName+"%");
            if(enterList != null) {
                for (EnterPrise enter:enterList){
                    List<Goods> goods = this.getGoodsOnSaleByShop().get(enter.getEnter_name());
                    int n = goods.size();
                    if(n>3){
                        for(int i=0;i<4;i++){
                            goods.get(i).setEnter_name(enter.getEnter_name());
                            goods.get(i).setGoods_firstP(this.shopService.getPicture(goods.get(i).getGoods_uuid()).get(0));
                        }
                        enter.setGoods_List(goods.subList(0,3));
                    }
                    else {
                        for(int i=0;i<n;i++){
                            goods.get(i).setEnter_name(enter.getEnter_name());
                            goods.get(i).setGoods_firstP(this.shopService.getPicture(goods.get(i).getGoods_uuid()).get(0));
                        }
                        enter.setGoods_List(goods.subList(0,n-1));
                    }
                }
                return enterList;
            }
        }
        return null;
    }
    /**
     * 商品名模糊查询方法
     */
    @RequestMapping(value = "getGoodsByName",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> getGoodsByName(@RequestParam("goodsName") String goodsName){
        if(goodsName != null){
            List<Goods> goodsList = shopService.getGoodsListByName("%"+goodsName+"%");
            if(goodsList != null) {
                for(Goods goods: goodsList) {
                    goods.setEnter_name(this.shopService.getEnterByUuid(goods.getEnter_uuid()).getEnter_name());
                    goods.setGoods_firstP(this.shopService.getPicture(goods.getGoods_uuid()).get(0));
                }
                return goodsList;
            }
        }
        return null;
    }
    /**
     * 价格低->高
     */
    @RequestMapping(value = "sortByLowPrice",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> sortByLowPrice(@RequestParam("goodsName") String goodsName) {
        if (goodsName != null) {
            List<Goods> goodsList = shopService.getGoodsListByName("%" + goodsName + "%");
            if (goodsList != null) {
                    goodsList.sort(Comparator.comparingDouble(Goods::getGoods_price));
                    for (Goods g : goodsList) {
                        g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                        g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                    }
                return goodsList;
                }
        }
        return null;
    }

    /**
     * 价格高->低
     */
    @RequestMapping(value = "sortByHighPrice",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> sortByHighPrice(@RequestParam("goodsName") String goodsName){
        if (goodsName != null) {
            List<Goods> goodsList = shopService.getGoodsListByName("%" + goodsName + "%");
            if (goodsList != null) {
                goodsList.sort((o1, o2) -> Double.compare(o2.getGoods_price(), o1.getGoods_price()));
                for (Goods g : goodsList) {
                    g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                    g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                }
                return goodsList;
            }
        }
        return null;
    }
    /**
     * 好评率排序
     */
    @RequestMapping(value = "sortByPraiseRate",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> sortByPraiseRate(@RequestParam("goodsName") String goodsName){
        if (goodsName != null) {
            List<Goods> goodsList = shopService.getGoodsListByName("%" + goodsName + "%");
            if (goodsList != null) {
                goodsList.sort((o1, o2) -> Double.compare(o2.getGoods_praise_rate(), o1.getGoods_praise_rate()));
                for (Goods g : goodsList) {
                    g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                    g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                }
                return goodsList;
            }
        }
        return null;
    }
    /**
     * 销量排序
     */
    @RequestMapping(value = "sortByGoodsVolume",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> sortByGoodsVolume(@RequestParam("goodsName") String goodsName){
        if (goodsName != null) {
            List<Goods> goodsList = shopService.getGoodsListByName("%" + goodsName + "%");
            if (goodsList != null) {
                goodsList.sort((o1, o2) -> Double.compare(o2.getGoods_volume(), o1.getGoods_volume()));
                for (Goods g : goodsList) {
                    g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                    g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                }
                return goodsList;
            }
        }
        return null;
    }

    /**
     * 分类展示商品
     */
    @RequestMapping(value = "getGoodsByType",method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> getGoodsByType(@RequestParam("goodsType")String goodsType){
        if (goodsType != null) {
          List<Goods> goods = this.shopService.getGoodsByType(goodsType);
          if(goods!=null){
              for(Goods g :goods){
                  g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                  g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
              }
              return goods;
          }
        }
        return null;
    }
    /**
     * 商家首页
     */
    @RequestMapping(value = "EnterHomePage",method = RequestMethod.POST)
    @ResponseBody
    public EnterPrise getEnterHomePage(@RequestParam("enterUuid") String enterUuid){
        if(enterUuid != null) {
            EnterPrise enterPrise = this.shopService.getEnterByUuid(enterUuid);
            List<Goods>  goods = this.shopService.getWholeGoods(enterUuid);
            if(goods!=null){
                for(Goods g:goods){
                    g.setEnter_name(enterPrise.getEnter_name());
                    g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                }
                enterPrise.setGoods_List(goods);
            }
            return enterPrise;
        }
        return null;
    }
    /**
     * 轮播图
     */
    @RequestMapping(value = "getLunbotu",method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> getLunbotu(){
            List<Goods> goodsList = shopService.getOnSaleGoods();
            if (goodsList != null) {
                goodsList.sort((o1, o2) -> Double.compare(o2.getGoods_praise_rate(), o1.getGoods_praise_rate()));
                for (Goods g : goodsList) {
                    g.setEnter_name(this.shopService.getEnterByUuid(g.getEnter_uuid()).getEnter_name());
                    g.setGoods_firstP(this.shopService.getPicture(g.getGoods_uuid()).get(0));
                }
                if(goodsList.size()<3)
                return goodsList.subList(0,goodsList.size());
                else return goodsList.subList(0,3);
            }

        return null;
    }
}

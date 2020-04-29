package com.xh.server.service;

import com.xh.server.config.MyConst;
import com.xh.server.dao.RedisDAO;
import com.xh.server.mapper.AccountMapper;
import com.xh.server.mapper.CartMapper;
import com.xh.server.mapper.OrdersMapper;
import com.xh.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CartService {

    @Autowired
    private RedisDAO rdao;

    @Autowired
    private AccountMapper adao;

    @Autowired
    private CartMapper cdao;

    @Autowired
    private OrdersMapper odao;

    public List<Cart> queryCart() {
        List<String> list = new ArrayList<>();
        list.add(MyConst.SESSIONID);
        List<String> list1 = rdao.executeRedisByLuaScript1(list,"getusername.lua");

        String username = list1.get(0);

        AccountExample example= new AccountExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Account> list2 = adao.selectByExample(example);
        List<String> list3 = new ArrayList<>();
        list3.add(list2.get(0).getUserid().toString());

        List<List> list4 = rdao.executeRedisByLuaScript(list3,"querycart.lua");

        List<Cart> list5 = new ArrayList<>();
        for(int i = 0;i<list4.get(0).size();i++){
            Cart cart = new Cart();
            Item item = (Item)list4.get(0).get(i);
            cart.setItem(item);
            cart.setItemid(item.getItemid());
            cart.setQuantity(Integer.parseInt(list4.get(1).get(i).toString()));
            cart.setOrderid(Integer.parseInt(list4.get(2).get(i).toString()));
            cart.setUserid(Integer.parseInt(list4.get(3).get(i).toString()));
            list5.add(cart);

        }
        return list5;
    }

    public void delCart(String iid) {
        //先删除redis中数据，再删除mysql中数据
        //删除redis
        List<String> list = new ArrayList<>();
        list.add(MyConst.SESSIONID);
        List<String> ulist = rdao.executeRedisByLuaScript1(list,"getusername.lua");

        String username = ulist.get(0);
        Account account = (Account) rdao.getHashTable("account",username);
        List<String> clist = new ArrayList<>();
        clist.add(account.getUserid().toString());
        clist.add(iid);
        List rlist = rdao.executeRedisByLuaScript1(clist,"delcart.lua");

        //删除mysql
        CartKey key = new CartKey();
        key.setItemid(Integer.parseInt(iid));
        key.setUserid(Integer.parseInt(rlist.get(1).toString()));
        key.setOrderid(Integer.parseInt(rlist.get(0).toString()));
        cdao.deleteByPrimaryKey(key);


    }

    public void putCart(String iid, String q) {
        //修改redis
        List<String> list = new ArrayList<>();
        list.add(MyConst.SESSIONID);
        List<String> ulist = rdao.executeRedisByLuaScript1(list,"getusername.lua");

        String username = ulist.get(0);
        Account account = (Account) rdao.getHashTable("account",username);
        List<String> clist = new ArrayList<>();

        clist.add(account.getUserid().toString());
        clist.add(iid);
        clist.add(q);
        List rlist = rdao.executeRedisByLuaScript1(clist,"updatecart.lua");

        //修改mysql
        Cart c = new Cart();
        c.setItemid(Integer.parseInt(iid));
        c.setUserid(Integer.parseInt(rlist.get(1).toString()));
        c.setOrderid(Integer.parseInt(rlist.get(0).toString()));
        c.setQuantity(Integer.parseInt(q));
        cdao.updateByPrimaryKey(c);

    }

    public void addCart(Cart cart) {

        List<String> list = new ArrayList<>();
        list.add(MyConst.SESSIONID);

        List<String> ulist = rdao.executeRedisByLuaScript1(list,"getusername.lua");
        String username = ulist.get(0);

        Account account = (Account) rdao.getHashTable("account",username);
        List<String> clist = new ArrayList<>();
        clist.add(account.getUserid().toString());//userid
        clist.add(cart.getItemid().toString());
        clist.add(cart.getQuantity().toString());

        List<Long> rlist = rdao.executeRedisByLuaScript(clist, "addcart.lua");
        Long flag = rlist.get(2);
        Long userid = rlist.get(3);
        Long quantity = rlist.get(1);
        Long maxid = rlist.get(0);
        //mysql
        if(flag==0){//1是未完成订单 0是新订单，需要先操作orders
            Orders orders = new Orders();
            orders.setOrderid(maxid.intValue());
            orders.setUserid(userid.intValue());
            odao.insert(orders);
        }
        //操作carts表
        Cart cart1 = new Cart();
        cart1.setOrderid(maxid.intValue());
        cart1.setQuantity(quantity.intValue());
        cart1.setItemid(cart.getItemid());
        cart1.setUserid(userid.intValue());
        cdao.insert(cart1);


    }

    public void checkout(float totalprice) {
        //redis 操作maxid
        List<String> list = new ArrayList<>();
        list.add(MyConst.SESSIONID);

        List<String> ulist = rdao.executeRedisByLuaScript1(list,"getusername.lua");
        String username = ulist.get(0);

        Account account = (Account) rdao.getHashTable("account",username);
        List<String> clist = new ArrayList<>();
        clist.add(account.getUserid().toString());//userid
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");
        String format = sdf.format(new Date());
        clist.add(format);//datatime
        clist.add(Float.toString(totalprice));//total

        List<Integer> rlist = rdao.executeRedisByLuaScript(clist, "checkout.lua");
    //mysql
        Integer userid=rlist.get(0);
        Integer orderid=rlist.get(1);
        Orders orders = new Orders();
        orders.setOrderid(orderid.intValue());
        orders.setUserid(userid.intValue());
        orders.setOrderdate(new Date());
        orders.setTotalprice(totalprice);
        odao.updateByPrimaryKey(orders);



    }
}

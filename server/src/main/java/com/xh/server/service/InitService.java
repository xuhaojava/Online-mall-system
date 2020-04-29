package com.xh.server.service;

import com.xh.server.config.MyConst;
import com.xh.server.dao.RedisDAO;
import com.xh.server.mapper.*;
import com.xh.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InitService {
    @Autowired
    private RedisDAO rdao;

    @Autowired
    private AccountMapper adao;

    @Autowired
    private ProfileMapper pdao;

    @Autowired
    private ProductMapper prdao;

    @Autowired
    private ItemMapper idao;

    @Autowired
    private CategoryMapper cdao;

    @Autowired
    private CartMapper cartdao;

    @Autowired
    private OrdersMapper odao;


    public void init(){//进行redis初始化。initAccount，initProfile，initCategory三个方法分别对3个表进行初始化
        //初始化之前需要将使用的redis数据库内的数据全部删除。
        //删除redis数据库的内容使用flushdb，通过lua操作redis。
        List<String> list=new ArrayList();
        list.add("8");//8是你的redis数据库名称
        rdao.executeRedisByLuaScript(list,"flushdb.lua");
        List<String> cookielist = new ArrayList<>();
        cookielist.add(MyConst.SESSIONID);
        rdao.executeRedisByLuaScript1(cookielist,"initcookie.lua");
        initAccount();
        initProfile();
        initCategory();
        initProduct();
        initItem();
        initCart();
        initOrders();
    }

    private void initAccount(){//初始化account表
        AccountExample example = new AccountExample();
        example.createCriteria().andUseridIsNotNull();//主键不为空
        List<Account> list = adao.selectByExample(example);
        //初始化之后需要输出数据，这里使用foreach，选择Hash格式
        //所以在写foreach之前需要自己写Hash
        list.forEach(c->rdao.setHashTable("account",c.getUsername(),c));
    }

    private void initProfile() {//初始化profile表
        ProfileExample example = new ProfileExample();
        example.createCriteria().andUseridIsNotNull();
        List<Profile> list = pdao.selectByExample(example);
        list.forEach(c->rdao.setHashTable("profile",c.getUserid().toString(),c));
    }

    private void initCategory() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andCatidIsNotNull();
        List<Category> list = cdao.selectByExample(example);
        list.forEach(c->rdao.setHashTable("category",c.getCatid().toString(),c));
    }

    private void initProduct(){
        ProductExample example=new ProductExample();
        example.createCriteria().andProductidIsNotNull();
        List<Product> list=prdao.selectByExample(example);
        list.forEach(c->rdao.setSet("product:"+c.getCatid()+":"+c.getProductid(),c));
    }

    private void initItem(){
        ItemExample example = new ItemExample();
        example.createCriteria().andProductidIsNotNull();
        List<Item> list = idao.selectByExample(example);
        list.forEach(c->rdao.setSet("item:"+c.getProductid()+":"+c.getItemid(),c));
    }

    private void initCart(){
        CartExample example = new CartExample();
        example.createCriteria().andUseridIsNotNull();
        List<Cart> list = cartdao.selectByExample(example);
        list.forEach(c->rdao.setString("carts:"+c.getUserid()+":"+c.getOrderid()+":"+c.getItemid(),c.getQuantity()));
    }

    private void initOrders(){
        OrdersExample example = new OrdersExample();
        //example.createCriteria().andOrderdateIsNotNull();//orders表中，日期不为空，即为已经支付过了。
        //需要储存已经完成的订单，所以需要将order表中的数据全部加载，所以不能使用isnotnull
        example.createCriteria().andUseridIsNotNull();//所有用户
        List<Orders> orderslist = odao.selectByExample(example);
        orderslist.forEach(o->{
            if(o.getOrderdate()!=null){
                //键是userid和orderid，值是orderdate，需要格式化date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String format = sdf.format(o.getOrderdate());
                rdao.setString("orders"+":"+o.getUserid()+":"+o.getOrderid(),format+":"+o.getTotalprice());
            }

        });
        AccountExample example1 = new AccountExample();
        example1.createCriteria().andUseridIsNotNull();
        List<Account> accountlist = adao.selectByExample(example1);
        accountlist.forEach(a->{
            Optional<Orders> op = orderslist.stream().filter(o->o.getUserid()==a.getUserid()).max((o1, o2)->o1.getOrderid()-o2.getOrderid());
            if(op.isPresent()){//已经找到了最大订单
                Orders max = op.get();
                //max.getOrderdate()==null代表订单未支付，即意味着可以继续购物。
                rdao.setString("maxid:"+max.getUserid(),max.getOrderdate()==null?"1"+max.getOrderid().toString():"0"+max.getOrderid().toString());

            }
        });


    }
}

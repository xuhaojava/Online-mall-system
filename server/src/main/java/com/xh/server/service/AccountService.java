package com.xh.server.service;

import com.xh.server.config.MyConst;
import com.xh.server.dao.RedisDAO;
import com.xh.server.mapper.AccountMapper;
import com.xh.server.mapper.ProfileMapper;
import com.xh.server.model.Account;
import com.xh.server.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper adao;
    @Autowired
    private ProfileMapper pdao;
    @Autowired
    private RedisDAO rdao;

    public Account login(Account account){
        Account a = (Account) rdao.getHashTable("account",account.getUsername());
        //登录时需要对密码进行对比，如果相同返回a如果不相同返回null
        if(a.getPassword().equals(account.getPassword())){
            //密码正确后需要改变redis，从游客变为用户
            List<String> list = new ArrayList();
            //KEYS1 SESSIONID
            //KEYS2 USERID
            list.add(MyConst.SESSIONID);
            list.add(a.getUsername());
            list.add(new Integer(3600).toString());
            rdao.executeRedisByLuaScript(list,"addcookie.lua");

            return a;//密码正确
        }
        return null;//密码错误
    }

    public void out(){//用户登出本系统
        //与登录一样，只不过执行的代码不同
        List<String> list = new ArrayList();
        list.add(MyConst.SESSIONID);
        rdao.executeRedisByLuaScript(list,"delcookie.lua");
    }


    public void in() {//游客进入本系统
        //操作redis
        //如果用户已经登录完成，但是刷新了主页，这时用户应该依然保持用户身份，而不是变为游客身份，
        //所以需要在lua脚本里进行判断
        List<String> list = new ArrayList();
        list.add(MyConst.SESSIONID);
        list.add("-1");
        rdao.executeRedisByLuaScript(list,"addguest.lua");

    }

    public boolean verify(String username) {
        return rdao.existsHashTable("account",username);


    }

    public void reg(Account account) {//对前端传过来的注册数据进行存储
        //问题，在前端向后端传值的过程中，profile和account的值里 userid都是空的
        //account可以自动+1，但是profile中的userid是不能的，所以需要将account的userid赋值给profile
        //将数据存入mysql
        adao.insert(account);
        Profile profile = account.getProfile();
        profile.setUserid(account.getUserid());
        pdao.insert(profile);
        //将数据存入redis
        rdao.setHashTable("account",account.getUsername(),account);
    }

    public String queryCookie() {
        List<String> list = new ArrayList();
        list.add(MyConst.SESSIONID);
        List list1 = rdao.executeRedisByLuaScript1(list,"querycookie.lua");
        String userid = list1.get(0).toString();
        return userid;
    }
}

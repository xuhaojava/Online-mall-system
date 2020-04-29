package com.xh.server.controller;

import com.xh.server.model.Account;
import com.xh.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(name = "account",value = {"/account"})
@RestController
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService service;

    @RequestMapping(value = {"/verify/{username}"})//校验注册
    public ResponseEntity<Void> verify(@PathVariable String username) {
        if (!service.verify(username)) {//返回true时，证明没有找到相同的用户名，代表这个用户可以注册
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.FOUND);
        }
    }

    @RequestMapping(value = {"/reg"},method = {RequestMethod.POST})
    public ResponseEntity<Void> reg(@RequestBody Account account){
        service.reg(account);
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = {"/login"})
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account account1=service.login(account);
        if(account1==null){
            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
        }else{
            //一开始未登录状态是游客cookie
            //登陆成功后需要将游客cookie改为用户cookie
            return new ResponseEntity<Account>(account1,HttpStatus.OK);
        }
    }

    @RequestMapping(value = {"/out"},method = {RequestMethod.GET})//用户登出
    public ResponseEntity<Void> out(){
        service.out();
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    @RequestMapping(value = {"/in"},method = {RequestMethod.GET})//游客进入
    public ResponseEntity<Void> in(){
        service.in();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/queryCookie"},method = {RequestMethod.GET})
    public ResponseEntity<String> queryCookie(){
        String  userid=service.queryCookie();
        return new ResponseEntity<String>(userid,HttpStatus.OK);
    }

}

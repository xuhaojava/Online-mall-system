package com.xh.server.controller;

import com.xh.server.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(name = "init",value = {"/init"})
@RestController
@CrossOrigin //跨域注解，由于init.html中url的端口号为8080而实际的网页的端口号改变了，根据HTTP协议，属于2个域，所以使用跨域注解，允许跨域访问
public class InitController {
    @Autowired
    private InitService service;

    @RequestMapping(value = {"/init"})
    public ResponseEntity<Void> init(){//没有东西返回到前端
        service.init();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

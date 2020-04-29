package com.xh.server.controller;

import com.xh.server.model.Account;
import com.xh.server.model.Category;
import com.xh.server.model.Item;
import com.xh.server.model.Product;
import com.xh.server.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(name = "pet",value = {"/pet"})
@RestController
@CrossOrigin
public class PetController {
    @Autowired
    private PetService service;

    @RequestMapping(value = {"/queryCategory"})
    public ResponseEntity<List<Category>> queryCategory(){
        List<Category> list = service.queryCategory();
        return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/querybycid/{cid}"})//categoryid
    public ResponseEntity<List<Product>> querybycid(@PathVariable String cid){
        List<Product> list = service.queryProduct(cid);
        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/querybypid/{pid}"})//productid
    public ResponseEntity<List<Item>> querybypid(@PathVariable String pid){
        List<Item> list = service.queryItems(pid);
        return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = {"/querybyiid/{iid}/{pid}"})//itemid
    public ResponseEntity<Item> querybyiid(@PathVariable String iid,@PathVariable String pid){
        Item item = service.queryItem(iid,pid);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }
}

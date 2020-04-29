package com.xh.server.controller;

import com.xh.server.model.Cart;
import com.xh.server.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(name = "cart",value = {"/cart"})
@RestController
@CrossOrigin
public class CartController {
    @Autowired
    private CartService service;

    @RequestMapping(value = {"/querycart"})
    public ResponseEntity<List<Cart>> queryCart(){
        List<Cart> cart = service.queryCart();
        return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = {"/del/{iid}"},method = RequestMethod.DELETE)
    public ResponseEntity<Void> delCart(@PathVariable String iid){
        service.delCart(iid);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/put/{iid}/{q}"},method = RequestMethod.PUT)
    public ResponseEntity<Void> putCart(@PathVariable String iid,@PathVariable String q){
        service.putCart(iid,q);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        service.addCart(cart);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @RequestMapping(value = {"/checkout/{total}"},method = RequestMethod.POST)
    public ResponseEntity<Void> checkout(@PathVariable String total){
        service.checkout(Float.parseFloat(total));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

package com.xh.server.service;

import com.xh.server.dao.RedisDAO;
import com.xh.server.mapper.CategoryMapper;
import com.xh.server.model.Category;
import com.xh.server.model.Item;
import com.xh.server.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class PetService {//操作redis，需要实例化一个redisdao
    @Autowired
    private RedisDAO rdao;


    public List<Category> queryCategory(){
        List<Category> list = (List<Category>)rdao.showHashTable("category");
        return list;
    }

    public List<Product> queryProduct(String cid) {
        String con = "product:"+cid+"*";
        Set set = rdao.keys(con);
        Set sp = rdao.unionSet(set);
        return new ArrayList<Product>(sp);
    }

    public List<Item> queryItems(String pid) {
        String con = "item:"+pid+":*";
        Set set = rdao.keys(con);
        Set sp = rdao.unionSet(set);
        return new ArrayList<Item>(sp);
    }

    public Item queryItem(String iid, String pid) {
        String item = "item:"+pid+":"+iid;
        LinkedHashSet ll = (LinkedHashSet)rdao.getSet(item);
        return (Item) ll.toArray()[0];
    }
}

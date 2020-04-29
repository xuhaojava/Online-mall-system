package com.xh.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Repository
public class RedisDAO {

    @Autowired
    private RedisTemplate<Serializable,Serializable> redisTemplate;//非字符串使用的

    @Autowired
    private RedisTemplate<Serializable,Serializable> redisTemplate1;//String使用的

    //为了加载数据，使用foreach，采用Hash格式，所以需要先解决Hash的代码

    public void setHashTable(String table,String k,Object v){
        //redis的hash需要传3个值，第一个是hash表名字，第二个是键，第三个是值
        HashOperations hashOperations=redisTemplate.opsForHash();
        hashOperations.put(table,k,v);
    }

    public Set<Object> unionSet(Set ss){
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.union("",ss);
    }

    public Set keys(String s){
        return redisTemplate.keys(s);
    }

    public void setSet(String table,Object v){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add(table,v);
    }

    public void setString(String table,Object v){
        //送入String的方法，需要使用redisTemplate1模版
        ValueOperations valueOperations = redisTemplate1.opsForValue();
        valueOperations.set(table,v);
    }

    public Set getSet(String table){
        SetOperations setOperations = redisTemplate.opsForSet();
        return setOperations.members(table);
    }

    public Object getHashTable(String table,String k){
        HashOperations hashOperations=redisTemplate.opsForHash();
        return hashOperations.get(table,k);
    }

    public boolean existsHashTable(String table,String k){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(table,k);
    }

    public Object showHashTable(String table){
        HashOperations hashOperations=redisTemplate.opsForHash();
        return hashOperations.values(table);
    }




    //调用lua脚本
    public List executeRedisByLuaScript(List in, String luaname){
        DefaultRedisScript<List> defaultRedisScript=new DefaultRedisScript();
        //从resources中取出脚本，放入到执行器中
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaname)));
        defaultRedisScript.setResultType(List.class);
        return redisTemplate.execute(defaultRedisScript,in);
    }

    public List executeRedisByLuaScript1(List in, String luaname){
        DefaultRedisScript<List> defaultRedisScript=new DefaultRedisScript();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaname)));
        defaultRedisScript.setResultType(List.class);
        return redisTemplate1.execute(defaultRedisScript,in);
    }



}

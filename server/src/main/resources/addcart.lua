--
-- Created by IntelliJ IDEA.
-- User: Administrator
-- Date: 2019/5/21
-- Time: 18:14
-- To change this template use File | Settings | File Templates.
--
--KEYS[1]-userid
--KEYS[2]-itemid
--KEYS[3]-quantity
local value=redis.call("get","maxid:"..tonumber(KEYS[1]))
local flag=string.sub(tostring(value),1,1)-- 1 继续购物 0 新生成订单
local maxid=string.sub(tostring(value),2)--orderid
if tonumber(flag)==0 then--新生成订单
    maxid=tonumber(maxid)+1
    redis.call("set","maxid:"..tonumber(KEYS[1]),"1"..maxid)--新订单
    flag=string.sub(tostring(value),1,1)--对新订单的在取一次
end
----cart flag
local newq=9
local cf=redis.call("exists","carts:"..KEYS[1]..":"..maxid..":"..KEYS[2])
if cf==0 then --不存在这笔数据，放心的把新数据加入到carts中
    newq=tonumber(KEYS[3])
    redis.call("set","carts:"..tonumber(KEYS[1])..":"..tonumber(maxid)..":"..tonumber(KEYS[2]),newq)
else       --==1 先取出该笔数据数量和新数量累加 再放到该笔数据下
    local oldq= redis.call("get","carts:"..KEYS[1]..":"..maxid..":"..KEYS[2])
    newq=oldq+tonumber(KEYS[3])
    redis.call("set","carts:"..KEYS[1]..":"..maxid..":"..KEYS[2],newq)
end
local a={tonumber(maxid),tonumber(newq),tonumber(flag),tonumber(KEYS[1]),tonumber(cf)}
--local a={tonumber(flag),tonumber(maxid)}
return a

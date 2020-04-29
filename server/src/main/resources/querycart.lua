--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/22
-- Time: 18:06
-- To change this template use File | Settings | File Templates.
--KEYS[1] = userid

local value = redis.call("get","maxid:"..KEYS[1])
local flag = string.sub(tostring(value),1,1)
local items = {}
local carts_quantity = {}
local orderid = {}
local result = {}
local userid = {}
local maxid = string.sub(tostring(value),2) --orderid
local a ={}
if tonumber(flag)==1 then
    a = redis.call("keys","carts:"..KEYS[1]..":"..maxid..":*")
    for i,v in ipairs(a) do
        local s1 = string.reverse(v)
        local l1 = string.find(s1,":") --返回第一个：的位置
        local s2 = string.sub(s1,1,l1-1)
        s2 = string.reverse(s2)
        a[i] = s2
        local k = redis.call("keys","item:*:"..s2)
        local item = redis.call("smembers",tostring(k[1]))
        items[i] = item[1] --item对象
        carts_quantity[i] = redis.call("get",v)
        orderid[i] = maxid
        userid[i] = KEYS[1]
    end
end
result={items,carts_quantity,orderid,userid}

return result
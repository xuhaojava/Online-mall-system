--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/23
-- Time: 11:29
-- To change this template use File | Settings | File Templates.
--


local value = redis.call("get","maxid:"..KEYS[1]) --KEYS[1] userid
local flag = string.sub(tostring(value),1,1)
local maxid = string.sub(tostring(value),2) --orderid

redis.call("del","carts:"..KEYS[1]..":"..tostring(maxid)..":"..KEYS[2])
local a={maxid,KEYS[1]}

return a

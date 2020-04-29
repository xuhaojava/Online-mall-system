--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/23
-- Time: 14:34
-- To change this template use File | Settings | File Templates.
--
--KEYS[1] userid
--KEYS[2] itemid
--KEYS[3] new quantity
local value = redis.call("get","maxid:"..KEYS[1]) --KEYS[1] userid
local flag = string.sub(tostring(value),1,1)
local maxid = string.sub(tostring(value),2) --orderid

redis.call("set","carts:"..KEYS[1]..":"..tostring(maxid)..":"..KEYS[2],KEYS[3])

local a={maxid,KEYS[1]}

return a
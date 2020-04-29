--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/23
-- Time: 17:42
-- To change this template use File | Settings | File Templates.
--
--KEYS[1] userid
--KEYS[2] datatime
--KEYS[3] total
local value = redis.call("get","maxid:"..tonumber(KEYS[1])) --KEYS[1] userid
local flag = string.sub(tostring(value),1,1)
local maxid = string.sub(tostring(value),2) --orderid

redis.call("set","maxid:"..KEYS[1],"0"..maxid)
redis.call("set","orders:"..KEYS[1]..":"..tonumber(maxid),KEYS[2]..":"..tonumber(KEYS[3]))

local a = {KEYS[1],maxid}
return  a
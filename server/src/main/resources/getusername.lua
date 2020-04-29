--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/19
-- Time: 14:32
-- To change this template use File | Settings | File Templates.
--
local username = redis.call("get",KEYS[1])

--local value = redis.call("get","maxid:"..username);
--local flag = sub(tostring(value),1,2);
--local maxid = sub(tostring(value),2)
--local array = {falg,maxid }


return username;



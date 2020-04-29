--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/18
-- Time: 17:47
-- To change this template use File | Settings | File Templates.
--

local username="-1"
if redis.call("exists",KEYS[1])==1 then
    username=redis.call("get",KEYS[1])
end
return username
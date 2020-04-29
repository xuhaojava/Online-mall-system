--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/9
-- Time: 19:51
-- To change this template use File | Settings | File Templates.
--
--修改登陆成功，值由-1变成userid
--KEYS[1]-sessionid
--KEYS[2]-userid
--设置有效时间
--KEYS[3]-time

--代表hset 表名 字段名 值
local flag = redis.call("exists",KEYS[1])
if flag==1 then
    redis.call("set",KEYS[1],KEYS[2]);
    redis.call("expire",KEYS[1],KEYS[3])
end
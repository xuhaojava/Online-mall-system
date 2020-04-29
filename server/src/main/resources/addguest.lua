--
-- Created by IntelliJ IDEA.
-- User: Lenovo
-- Date: 2019/11/10
-- Time: 19:56
-- To change this template use File | Settings | File Templates.
--
--对用户进行判断，如果值不等于-1，则代表该访问者的身份是用户而不是游客，这时不进行添加
--local userid = redis.call("get",KEYS[1])
--if userid~=-1 then
--userid不等于-1，证明已经完成了登录，所以不需要将身份变成游客
--    --所以无需进行操作
--else
--userid等于-1，证明是游客身份，所以添加游客身份
--redis.call("set",KEYS[1],KEYS[2])
--end
--问题，以上代码只对sessionid进行判断，如果登出删除了sessionid，不会在进行判断，导致登出后返回页面没有身份。
local flag = redis.call("exists", KEYS[1])
if flag == 0 then --如果返回为0，证明redis中没有sessionid，所以需要加入sessionid
    redis.call("set",KEYS[1],KEYS[2])
end
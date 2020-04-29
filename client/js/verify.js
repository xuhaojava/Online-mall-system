function verify() {
    var QUERY_COOKIE_URL="http://localhost:8080/account/queryCookie"
    $.ajax({
        url:QUERY_COOKIE_URL,
        type:"get",
        dataType:"text",
        statusCode:{
            200:function (data) {
                data.indexOf("-1")>=0?window.location="login.html":window.location="cart.html";
            },
            404:function () {

            }
        }
    });
}
function verify1() {
    var QUERY_COOKIE_URL="http://localhost:8080/account/queryCookie"
    $.ajax({
        url:QUERY_COOKIE_URL,
        type:"get",
        dataType:"text",
        statusCode:{
            200:function (data) {
                data.indexOf("-1")>=0?true:false;
            },
            404:function () {

            }
        }
    });
}
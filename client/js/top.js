$(function () {
    $.ajax({
        url:"../commons/top.html",
        type:"get",
        dataType:"text",
        statusCode:{
            200:function (data) {
                $("#top").html(data)
            }
        }

    })
})

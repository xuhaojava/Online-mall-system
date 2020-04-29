$(function () {
    $.ajax({
        url:"../commons/left.html",
        type:"get",
        dataType:"text",
        statusCode:{
            200:function (data) {
                $("#left").html(data)
            }
        }

    })
})
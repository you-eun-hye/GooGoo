function findPassword(){
    $.ajax({
        url:"/findUserPassword",
        type:"post",
        dataType:"text",
        data:{"mail" : $("#userEmail").val()},
        success: function(){
            alert("비밀번호 발송");
        }
    });
}
function find(){
    $.ajax({
        url:"/findUserPassword",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#userEmail").val()},
        success: function(){
            alert("비밀번호 발송");
        }
    });
}
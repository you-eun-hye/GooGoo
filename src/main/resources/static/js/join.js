function sendNumber(){
    $.ajax({
        url:"/mail",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#userEmail").val(), "nickname" : $("#userNickname").val()},
        success: function(data){
            if(data == "이미 가입된 메일입니다." || data == "이미 가입된 닉네임입니다."){
                alert(data);
            }
            else{
                alert("인증번호 발송");
                $("#Confirm").attr("value",data);
            }
        }
    });
}

function confirmNumber(){
    var number1 = $("#number").val();
    var number2 = $("#Confirm").val();

    if(number1 == number2){
        alert("인증되었습니다.");
    }else{
        alert("번호가 다릅니다.");
    }
}
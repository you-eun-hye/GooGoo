function sendNumber(){
    $.ajax({
        url:"/user/mypage/editUserEmail",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#userEmail").val()},
        success: function(data){
            alert("인증번호 발송");
            $("#Confirm").attr("value",data);
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

let index = {
    init: function (){
        $("#editBtn").on("click", () =>{
            this.update();
        });
    },

    update: function (){
        let data = {
            id: $("id").val(),
            userNickname: $("userNickname").val(),
            userEmail: $("userEmail").val(),
        };

        $.ajax({
            type: "PUT",
            url: "/user/mypage/editUser",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp){
            alert("정보가 수정되었습니다.");
            location.href = "/home";
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
}
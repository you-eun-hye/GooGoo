function checkNickname(){
    var userNickname = $("#userNickname").val();
    $.ajax({
        url:'/checkNickname',
        type: 'post',
        data:{userNickname:userNickname},
        success:function (cnt){
            if(cnt){
                $('.nicknackNo').css("display","inline-block");
                $('.nicknameOk').css("display", "none");
                alert("닉네임을 다시 입력해주세요");
                $('#userNickname').val('');
            }else{
                $('.nicknameOk').css("display","inline-block");
                $('.nicknackNo').css("display", "none");
            }
        },
        error:function (){
            alert("에러");
        }
    });
};

function checkEmail(){
    var userEmail = $("#userEmail").val();
    $.ajax({
        url:'/checkEmail',
        type: 'post',
        data:{userEmail:userEmail},
        success:function (cnt){
            if(cnt){
                $('.emailNo').css("display","inline-block");
                $('.emailOk').css("display", "none");
                alert("이메일을 다시 입력해주세요");
                $('#userEmail').val('');
            }else{
                $('.emailOk').css("display","inline-block");
                $('.emailNo').css("display", "none");
            }
        },
        error:function (){
            alert("에러");
        }
    });
};

function sendNumber(){
    $.ajax({
        url:"/mail",
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
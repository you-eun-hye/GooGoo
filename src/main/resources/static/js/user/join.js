function checkNickname(){
    var userNickname = $("#userNickname").val();
    $.ajax({
        url:'/nickname-validator',
        type: 'get',
        data:{userNickname:userNickname},
        success:function (cnt){
            if(cnt){
                $('.nicknameNo').css("display","inline-block");
                $('.nicknameOk').css("display", "none");
                alert("닉네임을 다시 입력해주세요");
                $('#userNickname').val('');
            }else{
                $('.nicknameOk').css("display","inline-block");
                $('.nicknameNo').css("display", "none");
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
        url:'/email-validator',
        type: 'get',
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
        url:"/email-sender",
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
    if($("#number").val() == $("#Confirm").val()){
        alert("인증되었습니다.");
    }else{
        alert("번호가 다릅니다.");
    }
}

function joinUser(){
    $.ajax({
            url: "/join",
            type: "post",
            data: JSON.stringify({
                "userNickname": $("#userNickname").val(),
                "userEmail": $("#userEmail").val(),
                "userPassword": $("#userPassword").val()
            }),
            contentType : "application/json; charset=utf-8",
            success : function (data, statusText, jqXHR) {
                alert("회원가입 성공");
                location.replace(`/login`);

                console.log(data);
                console.log(statusText);
                console.log(jqXHR);
            },
            error : function (jqXHR, testStatus, errorThrown) {
                alert("문제가 발생했습니다. 문의를 남겨주세요.");

                console.log(jqXHR);
                console.log(testStatus);
                console.log(errorThrown);
            }
    });
}

function editUser(){
    $.ajax({
        url: "/api/editUser",
        type: "patch",
        data: JSON.stringify({
            userNickname: $("#userNickname").val(),
            userEmail: $("#userEmail").val(),
            userPassword: $("#userPassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("회원 정보 수정 성공");
            location.replace(`/user/mypage`);
            console.log(this.data);
            console.log(data);
            console.log(statusText);
            console.log(jqXHR);
        },
        error : function (jqXHR, testStatus, errorThrown) {
            alert("문제가 발생했습니다. 문의를 남겨주세요.");
            console.log(this.data);
            console.log(jqXHR);
            console.log(testStatus);
            console.log(errorThrown);
        }
    });
}
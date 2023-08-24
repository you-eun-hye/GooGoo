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
        url:"/user/mypage/editUserEmail",
        type:"post",
        dataType:"json",
        data:{"mail" : $("#userEmail").val()},
        success: function(data){
            alert("인증번호 발송");
            $("#confirm").attr("value",data);
        }
    })
}

function confirmNumber(){
    var number1 = $("#number").val();
    var number2 = $("#confirm").val();

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
            userPassword: $("userPassword").val()
        };

        $.ajax({
            type: "PUT",
            url: "/user/mypage/editUser",
            data: JSON.stringify(data),
            dataType: "json"
        }).done(function (resp){
            alert("정보가 수정되었습니다.");
            location.href = "/home";
        }).fail(function (error){
            alert("에러가 발생했습니다. 문의를 남겨주세요.");
        });
    }
}
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

let index = {
    init: function (){
        $("#editBtn").on("click", () =>{
            this.update();
        });
    },

    update: function (){
        let data = {
            userNickname: $("userNickname").val(),
            userEmail: $("userEmail").val(),
            userPassword: $("userPassword").val()
        };

        $.ajax({
            type: "PUT",
            url: "/admin/user/edit",
            data: JSON.stringify(data),
            dataType: "json"
        }).done(function (resp){
            alert("정보가 수정되었습니다.");
            location.href = "/admin/index";
        }).fail(function (error){
            alert("에러가 발생했습니다. 문의를 남겨주세요.");
        });
    }
}
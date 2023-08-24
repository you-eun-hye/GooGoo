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
                alert("닉네임을 다시 입력해주세요");
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
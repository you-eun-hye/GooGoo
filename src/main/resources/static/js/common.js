/*
* 공용
*/

// 중복 닉네임 확인
function checkNickname(){
    var userNickname = $("#userNickname").val();
    $.ajax({
        url:'/api/duplication-nickname',
        type: 'GET',
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

// 중복 이메일 확인
function checkEmail(){
    var userEmail = $("#userEmail").val();
    $.ajax({
        url:'/api/duplication-email',
        type: 'GET',
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

/*
* 사용자
*/


// 유효 이메일 확인을 위한 인증번호 발송
function sendNumber(){
    $.ajax({
        url:"/api/certification-number",
        type:"POST",
        dataType:"json",
        data:{"mail" : $("#userEmail").val()},
        success: function(data){
            alert("인증번호 발송");
            $("#Confirm").attr("value",data);
        }
    });
}

// 인증번호 일치 여부
function confirmNumber(){
    if($("#number").val() == $("#Confirm").val()){
        alert("인증되었습니다.");
    }else{
        alert("번호가 다릅니다.");
    }
}

// 회원 가입
function joinUser(){
    $.ajax({
        url: "/api/join",
        type: "POST",
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

// 임시 비밀번호 발송
function findPassword(){
    $.ajax({
        url:"/api/password",
        type:"POST",
        dataType:"text",
        data:{"mail" : $("#userEmail").val()},
        success: function(){
            alert("비밀번호 발송");
        }
    });
}

// 회원 정보 수정
function editUser(){
    $.ajax({
        url: "/api/user",
        type: "PATCH",
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

// 회원 탈퇴
function deleteUser(){
    $.ajax({
        url: "/api/user",
        type: "DELETE",
        success : function (data, statusText, jqXHR) {
            alert("회원 탈퇴에 성공하였습니다.");
            location.replace(`/login`);
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

/*
* 관리자
*/

// 관리자 생성
function joinAdmin(){
    $.ajax({
        url: "/api/admin/join",
        type: "POST",
        data: JSON.stringify({
            "userEmail": $("#userEmail").val(),
            "userPassword": $("#userPassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("회원가입 성공");
            location.replace(`/admin`);

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

// 관리자 정보 수정
function editAdmin(){
    $.ajax({
        url: "/api/admin",
        type: "PATCH",
        data: JSON.stringify({
            id: $("#id").val(),
            userEmail: $("#userEmail").val(),
            userPassword: $("#userPassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("관리자 정보 수정 성공");
            location.replace(`/admin`);
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

// 관리자 삭제
function deleteAdmin(){
    $.ajax({
        url: "/api/admin/{id}",
        type: "DELETE",
        success : function (data, statusText, jqXHR) {
            alert("관리자 탈퇴에 성공하였습니다.");
            location.replace(`/admin`);
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

// 유저 정보 강제 수정
function forcingEditUser(){
    $.ajax({
        url: "/api/admin/user",
        type: "PATCH",
        data: JSON.stringify({
            userNickname: $("#userNickname").val(),
            userEmail: $("#userEmail").val(),
            userPassword: $("#userPassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("회원 정보 강제 수정 성공");
            location.replace(`/admin/user`);
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

// 회원 강제 탈퇴
function forcingDeleteUser(){
    $.ajax({
        url: "/admin/user/{id}",
        type: "DELETE",
        success : function (data, statusText, jqXHR) {
            alert("회원 강제 탈퇴에 성공하였습니다.");
            location.replace(`/admin/user`);
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

// 구글 비밀번호 임시 저장
function saveGooglePassword(){
    $.ajax({
        url: "/api/googlePassword",
        type: "POST",
        data: JSON.stringify({
            "googlePassword": $("#googlePassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            location.replace(`/buyCheck`);

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

// 문의글 작성
function saveBoard(){
    $.ajax({
        url: "/api/board/save",
        type: "POST",
        data: JSON.stringify({
            "boardTitle": $("#boardTitle").val(),
            "boardContent": $("#boardContent").val(),
            "input_file": $("#input_file").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("문의글 등록");
            location.replace(`/boad`);

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

// 문의글 수정
function editBoard(){
    $.ajax({
        url: "/api/board/edit",
        type: "PATCH",
        data: JSON.stringify({
            "boardTitle": $("#boardTitle").val(),
            "boardContent": $("#boardContent").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("수정 완료");
            location.replace(`/redirect:/board`);

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

// 공지글 작성
function saveNoti(){
    $.ajax({
        url: "/api/admin/noti/save",
        type: "POST",
        data: JSON.stringify({
            "boardTitle": $("#boardTitle").val(),
            "boardContent": $("#boardContent").val(),
            "input_file": $("#input_file").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("공지글 등록");
            location.replace(`redirect:/admin/noti`);

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

// 공지글 수정
function editNoti(){
    $.ajax({
        url: "/api/admin/noti/edit",
        type: "PATCH",
        data: JSON.stringify({
            "boardTitle": $("#boardTitle").val(),
            "boardContent": $("#boardContent").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("수정 완료");
            location.replace(`/redirect:/admin/noti`);

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

// 공지글 삭제
function deleteNoti(){
    $.ajax({
        url: "/api/admin/noti/delete/{id}",
        type: "DELETE",
        success : function (data, statusText, jqXHR) {
            alert("공지글 삭제 완료");
            location.replace(`redirect:/admin/noti`);
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
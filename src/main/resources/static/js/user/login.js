function loginUser(){
    $.ajax({
        url: "/api/v1/login",
        type: "post",
        data: JSON.stringify({
            "userEmail": $("#userEmail").val(),
            "userPassword": $("#userPassword").val()
        }),
        contentType : "application/json; charset=utf-8",
        success : function (data, statusText, jqXHR) {
            alert("GooGoo에 오신 걸 환영합니다!");
            location.replace(`/home`);

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
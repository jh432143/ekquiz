<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/resources/css/toastr.css">
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/jquery.min.js"></script>
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/jquery.form.min.js"></script>
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/toastr.min.js"></script>
</head>
<body>
<h3>아이디와 비밀번호를 입력해주세요.</h3>
    <form action="<c:out value="${pageContext.request.contextPath}"/>/loginProcess" method="post" name="actionForm" id="actionForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <p>
            <label for="email">아이디</label>
            <input type="text" id="email" name="email" />
        </p>
        <p>
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password"/>
        </p>
        <button type="button" class="btn" id="loginBtn">로그인</button>
    </form>
</body>
</html>
<script>
$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $("#loginBtn").on("click", function () {
        var params = $('#actionForm').serialize();
        $.ajax({
            url : "/test/loginCheck",
            //headers : {"X-CSRF-TOKEN" : token},
            data: params,
            method : "post",
            dataType:'json',
            async:false,
            cache:false,
            success: function (data) {
                console.log(data);
                if (data.status == "true") {
                    $("form[name=actionForm]").submit();
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });
    
    
    $("#actionForm").submit(function() {
        $(this).ajaxSubmit({
            headers: {
                "Accept" : "application/json; charset=utf-8"
            },
            beforeSubmit: function(){
            },
            success: function(data){
                data = $.parseJSON(data);
                console.log(data);
                if(data.success == "true") {
                    toastr.options.onHidden = function() {
                        location.href = data.redirectUrl;
                    }
                    toastr.success("로그인 되었습니다.");

                } else {
                    toastr.error("로그인 실패");
                }
            },
            error: function(data, status, err){
            }
        });
        return false;
    });
});
</script>
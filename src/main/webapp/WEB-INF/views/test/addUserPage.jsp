<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/include.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>퀴즈스푼</title>
    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/resources/css/toastr.css">
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/jquery.min.js"></script>
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/jquery.form.min.js"></script>
    <script src="<c:out value="${pageContext.request.contextPath}"/>/resources/js/toastr.min.js"></script>
</head>
<h1>quizBoard</h1>
<form action="/test/addUser" method="post" name="actionForm" id="actionForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="email_only_yn" value="Y"/>
    <input type="text" name="email">

    <button type="submit" class="btn" id="check_answer_btn">가입하기</button>
</form>

</body>
</html>
<script>
$(document).ready(function () {
    $("#actionForm").submit(function() {
        $(this).ajaxSubmit({
            headers: {
                "Accept" : "application/json; charset=utf-8"
            },
            beforeSubmit: function(){
            },
            success: function(data){
                console.log(data);
                if(data.status == "true") {
                    toastr.options.onHidden = function() {
                        location.href = "/login";
                    }
                    toastr.success("회원가입 완료.");

                } else {
                    toastr.error("회원가입 실패");
                }
            },
            error: function(data, status, err){
            }
        });
        return false;
    });
});
</script>
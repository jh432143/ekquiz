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
<form action="/main/checkAnswer" method="post" name="actionForm" id="actionForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="text" name="answer" value="kang정훈123">
</form>
<button type="button" class="btn" id="check_answer_btn">정답확인</button>
<a href="/logout">logout</a><br/>
<a id="findPassword">패스워드찾기</a><br/>
<a id="dbTest">DB TEST</a><br/>
<a id="deleteCache">DELETE Cache</a><br/>
<a href="/test/addUserPage">addUserPage</a><br/>
<a href="/test/board">board</a><br/><br/>
<sec:authorize access="isAnonymous()">
    <a href="/login">[login]</a><br/>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <p> [<sec:authentication property="principal.email"/>]님 안녕하세요</p>
    <a href="/logout">[logout]</a><br/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <a href="">[관리자 매뉴로 ㅇㅣ동]</a>
</sec:authorize>
</body>
</html>
<script>
$(document).ready(function () {
    $('#check_answer_btn').on('click', function () {
        var params = $('#actionForm').serialize();

        $.ajax({
            url:'/main/checkAnswer',
            data:params,
            method:'post',
            dataType:'json',
            async:false,
            cache:false,
            success: function (data) {
                console.log(data);
                if (data.status == 'true') {
                    toastr.success(data.msg);
                } else {
                    if (data.code == '1002') {
                        toastr.options.onHidden = function() {
                            location.href = '/login';
                        }

                        toastr.success(data.msg);
                    }
                }
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    $('#findPassword').on('click', function () {
        $.ajax({
            url:'/user/findPassword',
            data:'',
            method:'get',
            dataType:'json',
            async:false,
            cache:false,
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    $('#dbTest').on('click', function () {
        $.ajax({
            url:'/main/dbTest',
            data:'',
            method:'get',
            dataType:'json',
            async:false,
            cache:false,
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    $('#deleteCache').on('click', function () {
        $.ajax({
            url:'/main/deleteCache',
            data:'',
            method:'get',
            dataType:'json',
            async:false,
            cache:false,
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

});
</script>
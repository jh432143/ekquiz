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
    <form name="actionForm_1" id="actionForm_1">
    <h4>${testQuizList[0].quiz_key} | ${testQuizList[0].cash_prize}원문제</h4>
    <input type="hidden" name="quiz_key" value="${testQuizList[0].quiz_key}">
    <input type="text" name="answer" id="answer_1">
    <button type="button" class="btn" id="answer_check_btn_1">정답확인</button>

    <c:forEach items="${testQuizList[0].testShintList}" var="row" varStatus="status">
        <button type="button" class="btn" name="shint_btn_1" data-shint_key="${row.shint_key}">${row.shint}</button>
    </c:forEach>

    <c:forEach items="${testQuizList[0].testHintList}" var="row" varStatus="status">
        <p>${row.hint}</p>
    </c:forEach>
    </form>

    <form name="actionForm_2" id="actionForm_2">
    <h4>${testQuizList[1].quiz_key} | ${testQuizList[1].cash_prize}원문제</h4>
    <input type="hidden" name="quiz_key" value="${testQuizList[1].quiz_key}">
    <input type="text" name="answer" id="answer_2">
    <button type="button" class="btn" id="answer_check_btn_2">정답확인</button>

    <c:forEach items="${testQuizList[1].testShintList}" var="row" varStatus="status">
        <button type="button" class="btn">${row.shint}</button>
    </c:forEach>

    <c:forEach items="${testQuizList[1].testHintList}" var="row" varStatus="status">
        <p>${row.hint}</p>
    </c:forEach>
    </form>
</body>
</html>
<script>
$(document).ready(function () {
    $("#answer_check_btn_1").on("click", function () {
        var params = $('#actionForm_1').serialize();
        $.ajax({
            url:'/test/answerCheck',
            data:params,
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

        $("#answer_1").val('');
    });

    $("#answer_check_btn_2").on("click", function () {
        var params = $('#actionForm_2').serialize();
        $.ajax({
            url:'/test/answerCheck',
            data:params,
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

        $("#answer_2").val('');
    });

    $("button[name='shint_btn_1']").on("click", function () {
        alert($(this).data('shint_key'))
    });
});
</script>
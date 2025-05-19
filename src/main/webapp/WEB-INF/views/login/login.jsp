<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/login/login-header.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/login/login.css" />

<div class="login-wrapper">
    <div class="login-logo">
        <img src="${contextPath}/resources/images/logo.png" alt="인용문고 로고">
    </div>

    <h2 class="login-title">로그인</h2>

    <form method="post" action="${contextPath}/login" class="login-form">
        <label>아이디:
            <input type="text" name="userId" required />
        </label>
        <label>비밀번호:
            <input type="password" name="password" required />
        </label>
        <button type="submit">로그인</button>
    </form>
</div>

<script src="${contextPath}/resources/js/pages/login/login.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
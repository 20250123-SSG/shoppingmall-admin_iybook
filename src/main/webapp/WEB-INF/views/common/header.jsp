<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>IYBooks 운영자센터</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${contextPath}/resources/css/common.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

  <header class="header">
    <div class="header-title" onclick="location.href='${contextPath}/'">인용문고 관리자페이지</div>
    <div class="user-info">
      <i class="fas fa-bell"></i>
      <img src="https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-3.jpg" class="avatar">
      <c:if test="${not empty sessionScope.loginUser}">
        <span>${sessionScope.loginUser.userName}</span>
      </c:if>
    </div>
  </header>

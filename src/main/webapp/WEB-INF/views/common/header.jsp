<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>IYBooks 운영자센터</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${contextPath}/resources/css/common.css?v=20250520">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<header class="header">
  <div class="header-title" onclick="location.href='${contextPath}/main'">인용문고 관리자페이지</div>

  <div class="user-info">
    <i class="fas fa-bell"></i>
    <img src="https://www.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png" class="avatar">

    <c:if test="${not empty sessionScope.loginUser}">
      <span><strong>${sessionScope.loginUser.userName}</strong> 관리자님 </span>
      <form action="${contextPath}/logout.do" method="post" style="display:inline;">
        <button type="submit" class="logout-btn">로그아웃</button>
      </form>
    </c:if>
  </div>
</header>

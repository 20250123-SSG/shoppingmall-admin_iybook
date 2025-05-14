<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Title</title>
</head>
<body>
  <h1>메인페이지</h1>
  <a href="${contextPath}/menu/list.page">메뉴 목록 페이지로 이동</a>

  <!--
    1) 목록 기능
    /menu/list.page    ==포워딩==>  메뉴목록페이지 - /WEB-INF/views/menu/list.jsp (메뉴목록데이터 필요)
    2) 등록 기능
    /menu/regist.page  ==포워딩==>  메뉴등록페이지 - /WEB-INF/views/menu/regist.jsp
    /menu/regist.do   ==redirect==> /menu/list.page url 재요청
    3) 상세 기능
    /menu/detail.page?code=xx ==포워딩==> 메뉴상세페이지 - /WEB-INF/views/menu/detail.jsp (메뉴상세데이터 필요)
    4) 수정 기능
    /menu/modify.page?code=xx ==포워딩==> 메뉴수정페이지 - /WEB-INF/views/menu/modify.jsp (메뉴상세데이터 필요)
    /menu/modify.do   ==redirect==> /menu/detail.page?code=xx url 재요청
  -->
</body>
</html>

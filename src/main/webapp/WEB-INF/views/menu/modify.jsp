<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Title</title>
</head>
<body>
  <h2>메뉴 정보 수정</h2>

  <form action="${contextPath}/menu/modify.do" method="POST">
    <input type="hidden" name="menuCode" value="${menu.menuCode}">
    메뉴명: <input type="text" name="menuName" value="${menu.menuName}"> <br>
    메뉴가격: <input type="number" name="menuPrice" value="${menu.menuPrice}"> <br>
    카테고리번호: <input type="number" name="categoryCode" value="${menu.categoryCode}"> <br>
    판매여부:
    <input type="radio" name="orderableStatus" value="Y"> 판매
    <input type="radio" name="orderableStatus" value="N"> 미판매
    <br>
    <button type="submit">수정</button>
  </form>

  <script>
    document.querySelector('input[value=${menu.orderableStatus}]').setAttribute('checked', 'checked');
  </script>


</body>
</html>

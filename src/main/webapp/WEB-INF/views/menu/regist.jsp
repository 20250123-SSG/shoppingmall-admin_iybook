<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Title</title>
</head>
<body>

  <h2>메뉴 정보 등록</h2>

  <form action="${contextPath}/menu/regist.do" method="POST">
    메뉴명: <input type="text" name="menuName"> <br>
    메뉴가격: <input type="number" name="menuPrice"> <br>
    카테고리번호: <input type="number" name="categoryCode"> <br>
    판매여부:
    <input type="radio" name="orderableStatus" value="Y" checked> 판매
    <input type="radio" name="orderableStatus" value="N"> 미판매
    <br>
    <button type="submit">등록</button>
  </form>

</body>
</html>

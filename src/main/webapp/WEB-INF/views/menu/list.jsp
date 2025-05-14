<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Title</title>
</head>
<body>

  <c:if test="${not empty message}">
    <script>alert('${message}');</script>
  </c:if>

  <h2>판매중인 메뉴 목록</h2>

  <button onclick="location.href='${contextPath}/menu/regist.page';">신규 메뉴 등록 페이지로 이동</button>

  <form action="${contextPath}/menu/remove.do" method="POST">
    <button type="submit">일괄삭제</button>
    <table border="1">
      <thead>
        <tr>
          <th></th>
          <th>번호</th>
          <th>메뉴명</th>
          <th>메뉴가격</th>
          <th>카테고리번호</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty list}">
            <!--메뉴목록이 존재하지 않을 경우-->
            <tr>
              <td colspan="5" style="text-align:center;">판매가능한 메뉴가 존재하지 않습니다.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <!--메뉴목록이 존재할 경우-->
            <c:forEach var="m" items="${list}">
              <tr>
                <td><input type="checkbox" name="code" value="${m.menuCode}"></td>
                <td>${m.menuCode}</td>
                <td><a href="${contextPath}/menu/detail.page?code=${m.menuCode}">${m.menuName}</a></td>
                <td>${m.menuPrice}</td>
                <td>${m.categoryCode}</td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>

      </tbody>
    </table>
  </form>


</body>
</html>

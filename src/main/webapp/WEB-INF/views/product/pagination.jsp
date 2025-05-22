<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="pagination">
  <c:if test="${pageInfo.beginPage > 1}">
    <a href="#" data-page="1">&laquo;</a>
    <a href="#" data-page="${pageInfo.beginPage - 1}">&lt;</a>
  </c:if>

  <c:forEach var="page" begin="${pageInfo.beginPage}" end="${pageInfo.endPage}">
    <a href="#" data-page="${page}" class="${page == pageInfo.page ? 'active' : ''}">${page}</a>
  </c:forEach>

  <c:if test="${pageInfo.endPage < pageInfo.totalPage}">
    <a href="#" data-page="${pageInfo.endPage + 1}">&gt;</a>
    <a href="#" data-page="${pageInfo.totalPage}">&raquo;</a>
  </c:if>
</div>

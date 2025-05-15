<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/product.css">

  <div class="main">
    <h2>상품관리</h2>


  </div>

<script src="${contextPath}/resources/js/pages/product.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



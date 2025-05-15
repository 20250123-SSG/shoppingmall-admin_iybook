<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%
  String uri = request.getRequestURI(); // 예: /test/order/orderList.page
  String activeMenu = "";

  if (uri.contains("/notice")) activeMenu = "notice";
  else if (uri.contains("/product")) activeMenu = "product";
  else if (uri.contains("/settlement")) activeMenu = "settlement";
  else if (uri.contains("/statistics")) activeMenu = "statistics";
  else if (uri.contains("/sales")) activeMenu = "sales";

  request.setAttribute("activeMenu", activeMenu);  // JSP EL에서 접근 가능하도록 설정
%>

<div class="sidebar">
  <div class="sidebar-item ${activeMenu eq 'notice' ? 'active' : ''}" onclick="location.href='${contextPath}/notice'">
    <i class="fas fa-bullhorn"></i>
    <span>공지사항 관리</span>
  </div>
  <div class="sidebar-item ${activeMenu eq 'product' ? 'active' : ''}" onclick="location.href='${contextPath}/product'">
    <i class="fas fa-box"></i>
    <span>상품 관리</span>
  </div>
  <div class="sidebar-item ${activeMenu eq 'settlement' ? 'active' : ''}" onclick="location.href='${contextPath}/settlement'">
    <i class="fas fa-calculator"></i>
    <span>정산 관리</span>
  </div>
  <div class="sidebar-item ${activeMenu eq 'statistics' ? 'active' : ''}" onclick="location.href='${contextPath}/statistics'">
    <i class="fas fa-chart-line"></i>
    <span>통계</span>
  </div>
  <div class="sidebar-item ${activeMenu eq 'sales' ? 'active' : ''}" onclick="location.href='${contextPath}/sales/salesList.page'">
    <i class="fas fa-cart-shopping"></i>
    <span>판매 관리</span>
  </div>
</div>

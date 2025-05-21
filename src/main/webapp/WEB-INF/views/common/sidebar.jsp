<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%
  String uri = request.getRequestURI();
  String activeMenu = "";
  String activeSubmenu = "";

  if (uri.contains("/notice")) activeMenu = "notice";
  else if (uri.contains("/product")) {
    activeMenu = "product";
    if (uri.contains("/list")) activeSubmenu = "list";
    else if (uri.contains("/regist")) activeSubmenu = "regist";
  }
  else if (uri.contains("/settlement")) activeMenu = "settlement";
  else if (uri.contains("/statistics")) activeMenu = "statistics";
  else if (uri.contains("/sales")) {
    activeMenu = "sales";
    if (uri.contains("/salesList")) activeSubmenu = "salesList";
    else if (uri.contains("/orderControl")) activeSubmenu = "orderControl";
    else if (uri.contains("/cancelControl")) activeSubmenu = "cancelControl";
  }

  request.setAttribute("activeMenu", activeMenu);
  request.setAttribute("activeSubmenu", activeSubmenu);
%>

<div class="sidebar">

  <!-- 공지사항 관리 -->
  <div class="sidebar-item ${activeMenu eq 'notice' ? 'active' : ''}" onclick="location.href='${contextPath}/notice/noticeList.page'">
    <i class="fas fa-bullhorn"></i>
    <span>공지사항 관리</span>
  </div>

  <!-- 상품 관리 -->
  <div class="sidebar-item ${activeMenu eq 'product' ? 'active' : ''}" onclick="toggleSubmenu(this)">
    <i class="fas fa-box"></i>
    <span>상품 관리</span>
    <i class="fas fa-chevron-down arrow-icon ${activeMenu eq 'product' ? 'rotated' : ''}"></i>
  </div>
  <div class="sidebar-submenu ${activeMenu eq 'product' ? 'open' : ''}">
    <div class="sidebar-subitem ${activeSubmenu eq 'list' ? 'active' : ''}" onclick="location.href='${contextPath}/product/list.page'">상품 목록</div>
    <div class="sidebar-subitem ${activeSubmenu eq 'regist' ? 'active' : ''}" onclick="location.href='${contextPath}/product/regist.page'">상품 등록</div>
  </div>


  <!-- 주문 관리 -->
  <div class="sidebar-item ${activeMenu eq 'sales' ? 'active' : ''}" onclick="toggleSubmenu(this)">
    <i class="fas fa-cart-shopping"></i>
    <span>주문 관리</span>
    <i class="fas fa-chevron-down arrow-icon ${activeMenu eq 'sales' ? 'rotated' : ''}"></i>
  </div>
  <div class="sidebar-submenu ${activeMenu eq 'sales' ? 'open' : ''}">
    <div class="sidebar-subitem ${activeSubmenu eq 'salesList' ? 'active' : ''}" onclick="location.href='${contextPath}/sales/salesList.page'">주문 통합조회</div>
    <div class="sidebar-subitem ${activeSubmenu eq 'orderControl' ? 'active' : ''}" onclick="location.href='${contextPath}/sales/orderControl.page'">발주 및 발송관리</div>
    <div class="sidebar-subitem ${activeSubmenu eq 'cancelControl' ? 'active' : ''}" onclick="location.href='${contextPath}/sales/cancelControl.page'">취소 관리</div>
  </div>


  <!-- 정산 관리 -->
  <div class="sidebar-item ${activeMenu eq 'settlement' ? 'active' : ''}" onclick="location.href='${contextPath}/settlement/settleHome.page'">
    <i class="fas fa-calculator"></i>
    <span>정산 관리</span>
  </div>

  <div class="sidebar-item ${activeMenu eq 'statistics' ? 'active' : ''}" onclick="location.href='${contextPath}/statistics/stats.page'">
    <i class="fas fa-chart-line"></i>
    <span>통계</span>
  </div>

</div>

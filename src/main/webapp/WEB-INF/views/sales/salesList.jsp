<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/sales.css">

<div class="main">

  <form action="${contextPath}/sales/search.do" method="POST" class="search-sales-form">
    <div class="search-sales-group">
      <div class="search-sales-period-group">
        <label for="startDate" class="option-name">조회 기간</label>
        <div class="custom-period">
          <input type="date" class="date-period" id="startDate" name="startDate"> ~
          <input type="date" class="date-period" id="endDate" name="endDate">
        </div>
        <div class="btn-period">
          <button class="date-btn" id="todayBtn">오늘</button>
          <button class="date-btn" id="weekBtn">1주일</button>
          <button class="date-btn" id="monthBtn">1개월</button>
          <button class="date-btn" id="threeMonthBtn">3개월</button>
          <button class="date-btn" id="twoYearBtn">2년</button>
          <p class="info-text">※ 최대 조회 기간은 2년입니다.</p>
        </div>
      </div>
      <div class="order-status-checkboxes">
        <label class="option-name">주문 상태</label>
        <label><input type="checkbox" id="checkAllStatus" checked> 전체</label>
        <label><input type="checkbox" name="orderStatus" value="주문완료" checked> 주문완료</label>
        <label><input type="checkbox" name="orderStatus" value="취소요청" checked> 취소요청</label>
        <label><input type="checkbox" name="orderStatus" value="취소완료" checked> 취소완료</label>
        <label><input type="checkbox" name="orderStatus" value="배송완료" checked> 배송완료</label>
      </div>
      <div class="search-sales-userId-group">
        <label for="customerId" class="option-name">사용자 ID</label>
        <input type="text" id="customerId" name="customerId">
      </div>
      <div class="search-sales-orderId-group">
        <label for="orderId" class="option-name">주문 번호</label>
        <input type="text" id="orderId" name="orderId">
      </div>
      <button type="submit" class="btn btn-gray" id="search-sales-submit-button">조회</button>
    </div>
  </form>









  <h2>주문 관리</h2>
  <div style="margin-bottom: 1rem;">
    <button class="btn btn-gray"><i class="fa-solid fa-download"></i> 엑셀 다운로드</button>
    <button class="btn btn-red"><i class="fa-solid fa-plus"></i> 새 주문 등록</button>
  </div>

  <table border="1" cellspacing="0" cellpadding="8">
    <thead>
    <tr>
      <th><input type="checkbox"></th>
      <th>주문 ID</th>
      <th>주문일시</th>
      <th>고객 ID</th>
      <th>상품 정보</th>
      <th>주문 상태</th>
      <th>결제 금액</th>
      <th>관리</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td style="text-align: center;"><input type="checkbox"></td>
      <td>ORD-2025001</td>
      <td>2025-05-11 14:30</td>
      <td>user123</td>
      <td>책 제목 등 외 2권</td>
      <td style="text-align: center;">
        <select>
          <option>주문완료</option>
          <option>배송준비</option>
          <option>배송중</option>
          <option>배송완료</option>
        </select>
      </td>
      <td style="text-align: right;">45,000원</td>
      <td style="text-align: center;">
        <i class="fa-solid fa-eye"></i>
        <i class="fa-solid fa-pen"></i>
      </td>
    </tr>
    </tbody>
  </table>






</div>

<script src="${contextPath}/resources/js/pages/sales.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



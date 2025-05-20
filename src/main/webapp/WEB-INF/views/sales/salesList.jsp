<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/sales/sales.css">

<div class="main">

  <form action="${contextPath}/sales/salesList.page" method="GET" class="content-section" id="search-form">
    <div class="search-sales-group">
      <div class="search-sales-period-group">
        <label for="startDate" class="option-name">조회 기간</label>
        <div class="custom-period">
          <input type="date" class="date-period" id="startDate" name="startDate" value="${filter.startDate}"> ~
          <input type="date" class="date-period" id="endDate" name="endDate" value="${filter.endDate}">
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
        <input type="hidden" id="filterOrderStatus" value="${filter.orderStatus}">
        <label><input type="checkbox" id="checkAllStatus" checked> 전체</label>
        <label><input type="checkbox" name="orderStatus" value="주문완료"> 주문완료</label>
        <label><input type="checkbox" name="orderStatus" value="취소요청"> 취소요청</label>
        <label><input type="checkbox" name="orderStatus" value="취소완료"> 취소완료</label>
        <label><input type="checkbox" name="orderStatus" value="배송완료"> 배송완료</label>
      </div>
      <div class="search-sales-userId-group">
        <label for="customerId" class="option-name">사용자 ID</label>
        <input type="text" id="customerId" name="customerId" value="${filter.customerId}">
      </div>
      <div class="search-sales-orderId-group">
        <label for="orderId" class="option-name">주문 번호</label>
        <input type="text" id="orderId" name="orderId" value="${filter.orderId}">
      </div>
      <button type="submit" class="btn btn-gray" id="search-sales-submit-button">조회</button>
    </div>
  </form>

  <div class="content-section">
    <div class="order-list-header">
      <h2>주문 목록</h2>
      <p>총 ${orderCount}건</p>
    </div>
    <div class="table-section">
      <table border="1">
        <thead>
        <tr>
          <th width="40px">상세보기</th>
          <th>주문 ID</th>
          <th>고객 ID</th>
          <th>상태</th>
          <th>상품 수량</th>
          <th>총 결제금액</th>
          <th>결제수단</th>
          <th>주문일</th>
          <th>주문상태 변경일</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
          <c:when test="${empty orderListResult.orderList}">
            <tr><td colspan="8">조회된 주문이 없습니다.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="order" items="${orderListResult.orderList}">
              <tr class="selectable-row">
                <td id="order-detail-btn" style="text-align: center; cursor: pointer;"
                    onclick="location.href='${contextPath}/sales/orderDetail.page?orderId=${order.orderId}';">
                  👁
                </td>
                <td>${order.orderId}</td>
                <td>${order.customerId}</td>
                <c:choose>
                  <c:when test="${order.orderStatus == '주문완료'}">
                    <td style="color: blue; text-align: center;">${order.orderStatus}</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center">${order.orderStatus}</td>
                  </c:otherwise>
                </c:choose>
                <td style="text-align: right"><fmt:formatNumber value="${order.orderTotalCount}"/></td>
                <td style="text-align: right"><fmt:formatNumber value="${order.orderTotalPrice}"/></td>
                <td style="text-align: center">${order.payment}</td>
                <td style="text-align: center">${order.getFormattedOrderDate()}</td>
                <c:choose>
                  <c:when test="${order.updateDate == order.orderDate}">
                    <td style="text-align: center">-</td>
                  </c:when>
                  <c:otherwise>
                    <td style="text-align: center">${order.getFormattedUpdateDate()}</td>
                  </c:otherwise>
                </c:choose>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
        </tbody>
      </table>

      <ul class="pagination">
        <c:url var="prevUrl" value="/sales/salesList.page">
          <c:param name="page" value="${orderListResult.page - 1}" />
          <c:param name="startDate" value="${filter.startDate}" />
          <c:param name="endDate" value="${filter.endDate}" />
          <c:param name="customerId" value="${filter.customerId}" />
          <c:param name="orderId" value="${filter.orderId}" />
          <c:forEach var="status" items="${filter.orderStatus}">
            <c:param name="orderStatus" value="${status}" />
          </c:forEach>
        </c:url>
        <li class="page-item ${orderListResult.page == 1 ? 'disabled' : ''}">
          <a class="page-link" href="${prevUrl}">Previous</a>
        </li>
        <c:forEach var="p" begin="${orderListResult.beginPage}" end="${orderListResult.endPage}">
          <c:url var="pageUrl" value="/sales/salesList.page">
            <c:param name="page" value="${p}" />
            <c:param name="startDate" value="${filter.startDate}" />
            <c:param name="endDate" value="${filter.endDate}" />
            <c:param name="customerId" value="${filter.customerId}" />
            <c:param name="orderId" value="${filter.orderId}" />
            <c:forEach var="status" items="${filter.orderStatus}">
              <c:param name="orderStatus" value="${status}" />
            </c:forEach>
          </c:url>
          <li class="page-item ${p == orderListResult.page ? 'active' : ''}">
            <a class="page-link" href="${pageUrl}">${p}</a>
          </li>
        </c:forEach>
        <c:url var="nextUrl" value="/sales/salesList.page">
          <c:param name="page" value="${orderListResult.page + 1}" />
          <c:param name="startDate" value="${filter.startDate}" />
          <c:param name="endDate" value="${filter.endDate}" />
          <c:param name="customerId" value="${filter.customerId}" />
          <c:param name="orderId" value="${filter.orderId}" />
          <c:forEach var="status" items="${filter.orderStatus}">
            <c:param name="orderStatus" value="${status}" />
          </c:forEach>
        </c:url>
        <li class="page-item ${orderListResult.page == orderListResult.totalPage ? 'disabled' : ''}">
          <a class="page-link" href="${nextUrl}">Next</a>
        </li>
      </ul>
    </div>

  </div>

</div>

<script>
  const contextPath = "${contextPath}";
</script>
<script src="${contextPath}/resources/js/pages/sales/salesList.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/statistics.css">

<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<p>데이터 개수: ${fn:length(summaryList)}</p>


<div class="main">
  <h2 class="statistics-title">통계</h2>

  <!-- 통계 조회 폼 -->
  <div class="stats-form-group">
    <label>시작일:
      <span id="start-container">
      <input type="date" id="start-date" name="startDate" required>
    </span>
    </label>
    <label>종료일:
      <span id="end-container">
      <input type="date" id="end-date" name="endDate" required>
    </span>
    </label>
    <label>단위:
      <select id="granularity" name="granularity">
        <option value="DAY">일별</option>
        <option value="MONTH">월별</option>
        <option value="YEAR">연별</option>
      </select>
    </label>
    <button type="button" id="search-btn">조회</button>
  </div>


  <!-- 📅 이번달 요약 -->
  <c:choose>
  <c:when test="${granularity == 'DAY'}">
  <div class="summary-box">
    <span>📅오늘 총 매출: <strong><fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="₩"/></strong></span>
  </div>
  </c:when>
  <c:when test="${granularity == 'MONTH'}">
  <div class="summary-box">
    <span>📅이번 달 총 매출: <strong><fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="₩"/></strong></span>
  </div>
  </c:when>
  <c:otherwise>
  <div class="summary-box">
    <span>📅올해 총 매출: <strong><fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="₩"/></strong></span>
  </div>
  </c:otherwise>
  </c:choose>


  <!-- 통계 테이블 -->
  <table class="statistics-table">
    <thead>
    <tr>
      <th>일자</th>
      <th>매출액</th>
      <th>주문수</th>
      <th>취소건</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="stat" items="${summaryList}">
      <c:choose>
        <c:when test="${fn:length(stat.statisticsDate) == 4}">
          <c:set var="parsedDate" value="${stat.statisticsDate}-01-01" />
        </c:when>
        <c:when test="${fn:length(stat.statisticsDate) == 7}">
          <c:set var="parsedDate" value="${stat.statisticsDate}-01" />
        </c:when>
        <c:otherwise>
          <c:set var="parsedDate" value="${stat.statisticsDate}" />
        </c:otherwise>
      </c:choose>

      <fmt:parseDate value="${parsedDate}" var="parsedDateObj" pattern="yyyy-MM-dd" />

      <tr class="clickable-row"
          data-date="${stat.statisticsDate}"
          data-unit="${granularity}">
        <td><fmt:formatDate value="${parsedDateObj}" pattern="yyyy년 M월 d일" /></td>
        <td><fmt:formatNumber value="${stat.totalSales}" type="currency" currencySymbol="₩" /></td>
        <td>${stat.orderCount}건</td>
        <td>${stat.cancelCount}건</td>
      </tr>
    </c:forEach>

    <!-- 합계 행 -->
    <tr class="summary-row">
      <td><strong>합계</strong></td>
      <td><strong><fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="₩"/></strong></td>
      <td><strong>${totalOrderCount}건</strong></td>
      <td><strong>${totalCancelCount}건</strong></td>
    </tr>
    </tbody>
  </table>



  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${contextPath}/resources/js/pages/statistics.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/statistics.css">

<div class="main">
  <h2>통계</h2>

  <!-- 통계 조회 폼 -->
  <form id="stats-form">
    <label>
      시작일:
      <input type="date" name="startDate" required />
    </label>
    <label>
      종료일:
      <input type="date" name="endDate" required />
    </label>
    <label>
      단위:
      <select name="granularity">
        <option value="DAY">일별</option>
        <option value="MONTH">월별</option>
        <option value="YEAR">연별</option>
      </select>
    </label>
    <button type="submit">조회</button>
  </form>

  <!-- 차트 영역 -->
  <div class="charts">
    <div class="chart-card">
      <h3>매출 금액</h3>
      <canvas id="salesChart"></canvas>
    </div>
    <div class="chart-card">
      <h3>주문 / 취소 건수</h3>
      <canvas id="orderChart"></canvas>
    </div>
  </div>

  <!-- 카테고리별 통계 차트 -->
  <div class="chart-card">
    <h3>카테고리별 판매량</h3>
    <canvas id="categoryChart"></canvas>
  </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${contextPath}/resources/js/pages/statistics.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

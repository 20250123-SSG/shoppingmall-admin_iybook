<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/statistics.css">

<div class="main">
  <h2 class="statistics-title">통계</h2>

  <!-- 카테고리 조회 폼 -->
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

  <!-- 차트 -->
  <div class="chart-container">
    <canvas id="myChart" width="600" height="300"></canvas>
  </div>

  <br>

  <!-- 통계 테이블 -->
  <h3>전체 고객 기준</h3>
  <table border="1">
    <thead>
    <tr><th>카테고리명</th><th>주문수</th></tr>
    </thead>
    <tbody id="all-body"></tbody>
  </table>

  <h3>성별 기준</h3>
  <table border="1">
    <thead>
    <tr><th>성별</th><th>카테고리명</th><th>주문수</th></tr>
    </thead>
    <tbody id="gender-body"></tbody>
  </table>

  <h3>나이대 기준</h3>
  <table border="1">
    <thead>
    <tr><th>나이대</th><th>카테고리명</th><th>주문수</th></tr>
    </thead>
    <tbody id="age-body"></tbody>
  </table>

<!-- 자바스크립트 -->
<script>
  const contextPath = "${contextPath}";
</script>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${contextPath}/resources/js/pages/statistics.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

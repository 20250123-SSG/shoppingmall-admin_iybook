<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<style>
  .stats-form-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    margin-bottom: 20px;
  }

  .tab-link-style {
    background: none;
    border: none;
    color: #4E79A7;
    font-weight: bold;
    cursor: pointer;
    font-size: 15px;
    padding: 0;
    text-decoration: underline;
  }

  .tab-link-style:hover {
    color: #2d5d90;
    text-decoration: underline;
  }

</style>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/statistics.css">

<div class="main">
  <h2 class="statistics-title">통계</h2>
  <div class="top-tabs" style="margin-bottom: 20px;">
  </div>

  <br>

  <!-- 통계 조회 폼 -->
  <div class="stats-form-wrapper">
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
    <div class="right-button">
      <button onclick="location.href='${contextPath}/statistics/statsCategory.page'" class="tab-link-style">
        카테고리 통계 보기
      </button>
    </div>
  </div>

  <br>

  <!-- 총 매출 요약 출력 영역 -->
  <div class="summary-box" id="total-summary"></div>

  <!-- 차트 -->
  <div class="chart-container">
    <canvas id="myChart" width="600" height="300"></canvas>
  </div>

  <br>

  <!-- 통계 테이블 -->
  <table class="statistics-table">
    <thead>
    <tr>
      <th>일자</th>
      <th>매출액</th>
      <th>주문건</th>
      <th>취소건</th>
    </tr>
    </thead>
    <tbody id="result-body">
    </tbody>
  </table>
</div>

<!-- 자바스크립트 -->
<script>
  const contextPath = "${contextPath}";
</script>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${contextPath}/resources/js/pages/statistics/statistics.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

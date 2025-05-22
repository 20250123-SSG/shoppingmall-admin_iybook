<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/statistics.css">

<style>
  .chart-row {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    margin-top: 10px;
    justify-content: flex-start;
  }

  .chart-box {
    flex: 1 1 250px;
    max-width: 250px;
    text-align: center;
  }

  .chart-box canvas {
    display: block;
    width: 100% !important;
    max-width: 250px;
    height: 250px !important;
    aspect-ratio: 1 / 1;
    margin: 0 auto;
  }

  .tab-buttons {
    margin: 20px 0 10px;
  }

  .tab-buttons button {
    margin-right: 8px;
    padding: 6px 16px;
    font-weight: bold;
    border: none;
    border-radius: 6px;
    background-color: #eee;
    cursor: pointer;
  }

  .tab-buttons button.active {
    background-color: #4E79A7;
    color: white;
  }

  .tab-content {
    margin-top: 10px;
  }

  .stats-form-group {
    display: flex;
    align-items: center;
    gap: 16px;
    flex-wrap: wrap;
    margin-bottom: 20px;
  }

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

  .stats-form-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    margin-bottom: 20px;
  }

</style>

<div class="main">
  <h2 class="statistics-title">통계</h2>
  <div class="top-tabs" style="margin-bottom: 20px;">

    <!-- 날짜 선택 -->
    <div class="top-tabs stats-form-wrapper">
      <div class="stats-form-group">
        <label>시작일:
          <span id="start-container">
        <input type="month" id="start-date" name="startDate" required>
      </span>
        </label>
        <label>종료일:
          <span id="end-container">
        <input type="month" id="end-date" name="endDate" required>
      </span>
        </label>
        <button type="button" id="search-btn">조회</button>
      </div>

      <div class="right-button">
        <button onclick="location.href='${contextPath}/statistics/stats.page'" class="tab-link-style">
          판매액 통계 보기
        </button>
      </div>
    </div>

    <!-- 탭 버튼 -->
    <div class="tab-buttons">
      <button type="button" class="tab-btn active" data-tab="tab-all">전체 기준</button>
      <button type="button" class="tab-btn" data-tab="tab-gender">성별 기준</button>
      <button type="button" class="tab-btn" data-tab="tab-age">나이대 기준</button>
    </div>

    <!-- 탭 내용 -->
    <div id="tab-all" class="tab-content">
      <div class="chart-row">
        <div class="chart-box">
          <canvas id="categoryChart"></canvas>
        </div>
      </div>
    </div>

    <div id="tab-gender" class="tab-content" style="display: none;">
      <div id="gender-chart-container" class="chart-row"></div>
    </div>

    <div id="tab-age" class="tab-content" style="display: none;">
      <div id="age-chart-container" class="chart-row"></div>
    </div>

    <!-- JS 리소스 로딩 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
      const contextPath = "${contextPath}";
    </script>

    <script src="${contextPath}/resources/js/pages/statistics/statisticsCategory.js"></script>
  </div>

  <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

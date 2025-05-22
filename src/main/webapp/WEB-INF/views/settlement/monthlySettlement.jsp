<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/settlement.css">


<!-- 조회 조건 -->
<div class="search-area">
  <div class="row align-items-center">
    <div class="col-auto">
      <label for="settlementMonth" class="form-label mb-0">정산월</label>
      <input type="month" class="form-control" id="settlementMonth" name="settlementMonth">
    </div>
    <hr>
    <div class="col-auto">
      <button type="button" class="btn btn-primary btn-gray" onclick="searchMonthlySettlement()">검색</button>
      <button type="button" class="btn btn-secondary btn-red" onclick="resetSearch()">초기화</button>
    </div>
  </div>
</div>

<!-- 정산내역 목록 -->
<div class="table-responsive">
  <table class="table table-bordered table-hover text-center align-middle product-table">
    <thead>
    <tr>
      <th>정산기준일</th>
      <th>정산예정일</th>
      <th>정산완료일</th>
      <th>정산금액</th>
      <th>수수료합계</th>
    </tr>
    </thead>
    <tbody id="monthlySettlementBody">
    <tr>
      <td colspan="5" class="text-center py-4">조회할 월을 선택해주세요.</td>
    </tr>
    </tbody>
  </table>
</div>

<script>
  document.addEventListener('DOMContentLoaded', function () {
    // 현재 월을 기본값으로 설정
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    document.getElementById('settlementMonth').value = `${year}-${month}`;

    // 초기 데이터 로드
    searchMonthlySettlement();
  });

  function searchMonthlySettlement() {
      const monthInput = document.getElementById('settlementMonth');
      const selectedMonth = monthInput.value;
      const tbody = document.getElementById('monthlySettlementBody');

      if (!selectedMonth) {
          tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4">조회할 월을 선택해주세요.</td></tr>';
          return;
      }

      tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4">데이터를 불러오는 중입니다...</td></tr>';
      fetch('${contextPath}/settlement/monthly?month=' + selectedMonth) // 컨트롤러 URL로 요청
        .then(response => {
            console.log(response);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON 데이터를 파싱
        })
        .then(data => {
            console.log('Received data:', data);
            if (data && data.length > 0) {
                tbody.innerHTML = data.map(row => `
                    <tr>
                        <td>\${row.stDate ? new Date(row.stDate).toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }) : ''}</td>
                        <td>\${row.exDate ? new Date(row.exDate).toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }) : ''}</td>
                        <td>\${row.compDate ? new Date(row.compDate).toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }) : ''}</td>
                        <td class="text-end">\${new Intl.NumberFormat('ko-KR').format(row.stPrice)}원</td>
                        <td class="text-end">\${new Intl.NumberFormat('ko-KR').format(row.tax)}원</td>
                    </tr>
                `).join('');
            } else {
                tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4">데이터가 존재하지 않습니다.</td></tr>';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            tbody.innerHTML = '<tr><td colspan="5" class="text-center py-4">데이터를 불러오는데 실패했습니다.</td></tr>';
        });
  }

  function resetSearch() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    document.getElementById('settlementMonth').value = `${year}-${month}`;
    searchMonthlySettlement();
  }

</script> 
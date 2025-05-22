<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/settlement.css">

<!-- 정산내역 상세 목록 -->
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
        <tbody id="settlementListBody">
        <tr>
            <td colspan="5" class="text-center py-4">데이터를 불러오는 중입니다...</td>
        </tr>
        </tbody>
    </table>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    loadDetailSettlementData();
});

function loadDetailSettlementData() {
    const tbody = document.getElementById('settlementListBody');
    fetch('${contextPath}/settlement/settlementList')
        .then(response => response.json())
        .then(data => {
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
                tbody.innerHTML = '<tr><td colspan="7" class="text-center py-4">데이터가 존재하지 않습니다.</td></tr>';
            }
        })
        .catch(error => {
            console.error('Error:', error);
            tbody.innerHTML = '<tr><td colspan="7" class="text-center py-4">데이터를 불러오는데 실패했습니다.</td></tr>';
        });
}
</script> 
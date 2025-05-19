<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 정산내역 상세 목록 -->
<div class="table-responsive">
    <table class="table table-bordered table-hover text-center align-middle">
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
            <td colspan="7" class="text-center py-4">데이터를 불러오는 중입니다...</td>
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
    fetch('${contextPath}/api/settlement/settlementList.page')
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                tbody.innerHTML = data.map(detail => {
                    const orderDate = detail.orderDate ? new Date(detail.orderDate).toLocaleDateString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    }).replace(/\. /g, '-').replace('.', '') : '';

                    const expectedDate = detail.expectedSettleDate ? new Date(detail.expectedSettleDate).toLocaleDateString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    }).replace(/\. /g, '-').replace('.', '') : '';

                    const completedDate = detail.settleCompletedDate ? new Date(detail.settleCompletedDate).toLocaleDateString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit'
                    }).replace(/\. /g, '-').replace('.', '') : '';

                    return `
                        <tr>
                            <td>\${detail.orderNo}</td>
                            <td class="text-start">\${detail.productName}</td>
                            <td>\${orderDate}</td>
                            <td class="text-end">\${new Intl.NumberFormat('ko-KR').format(detail.paymentAmount)}원</td>
                            <td>\${detail.settleStatus}</td>
                            <td>\${expectedDate}</td>
                            <td>\${completedDate}</td>
                        </tr>
                    `;
                }).join('');
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
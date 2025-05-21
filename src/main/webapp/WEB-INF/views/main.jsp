<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>
<link rel="stylesheet" href="${contextPath}/resources/css/home.css">

<div class="main">
    <div class="main2">
        <div class="dashboard-container">

            <div class="top-summary-container">
                <div class="summary-box">섹션 1</div>
                <div class="summary-box">섹션 2</div>
                <div class="summary-box">섹션 3</div>
            </div>

            <div class="content-sections">
                <div class="notice-section">
                    <h2>최신 공지사항</h2>
                    <table class="notice-table">
                        <thead>
                        <tr>
                            <th>No</th>
                            <th>제목</th>
                            <th>내용</th>
                            <th>숨김 여부</th>
                        </tr>
                        </thead>
                        <tbody id="notice-body">
                        <tr>
                            <td colspan="4">로딩 중...</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="settlement-section">
                    <h2>월별 정산 금액 차트</h2>
                    <canvas id="myChart"></canvas>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    fetch('${contextPath}/notice/home')
      .then(response => response.json())
      .then(data => {
          const tbody = document.getElementById('notice-body');
          tbody.innerHTML = ''; // 기존 로딩 메시지 제거

          if (data.length === 0) {
              tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">등록된 공지사항이 없습니다.</td></tr>';
              return;
          }

          data.forEach(notice => {
              const title = notice.title.length > 10
                ? notice.title.substring(0, 10) + '...'
                : notice.title

              const description = notice.description.length > 30
                ? notice.description.substring(0, 30) + '...'
                : notice.description;

              const publishStatus = notice.publishStatus === '숨김'
                ? '<span class="status-red">숨김</span>'
                : '<span class="status-blue">게시</span>';

              const row = `
                    <tr>
                        <td>\${notice.noticeId}</td>
                        <td><a href="${contextPath}/notice/noticeDetail.do?noticeId=\${notice.noticeId}">\${title}</a></td>
                        <td>\${description}</td>
                        <td>\${publishStatus}</td>
                    </tr>
                `;
              tbody.insertAdjacentHTML('beforeend', row);
          });

          console.log(tbody)
      })
      .catch(error => {
          console.error('공지사항 로딩 실패:', error);
          document.getElementById('notice-body').innerHTML =
            '<tr><td colspan="6" style="text-align:center;">공지사항을 불러오지 못했습니다.</td></tr>';
      });
</script>

<%--차트--%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const ctx = document.getElementById('myChart');
    let myChart = null; // 차트 인스턴스를 저장할 변수

    // 월별 정산 데이터를 가져와 그래프를 그리는 함수
    function loadMonthlySettlementChart() {
        fetch('${contextPath}/settlement/settlementList')
            .then(response => response.json())
            .then(data => {
                if (data && data.length > 0) {
                    // 1. 월별로 데이터 합산 및 그룹화
                    const monthlyData = {}; // { 'YYYY-MM': totalStPrice } 형태
                    data.forEach(row => {
                        if (row.stDate) {
                            const date = new Date(row.stDate);
                            // 한국 시간 기준으로 월을 가져오기 위해 toLocaleString 사용
                            // 'YYYY년 M월' 형식으로 키 생성
                            const yearMonth = date.getFullYear() + '년 ' + (date.getMonth() + 1) + '월';
                            const stPrice = row.stPrice || 0; // stPrice가 없을 경우 0으로 처리

                            if (monthlyData[yearMonth]) {
                                monthlyData[yearMonth] += stPrice;
                            } else {
                                monthlyData[yearMonth] = stPrice;
                            }
                        }
                    });

                    // 월별 데이터를 정렬 (오래된 월부터)
                    const sortedMonths = Object.keys(monthlyData).sort((a, b) => {
                        // 'YYYY년 M월' 형식에서 'YYYYMM' 형태로 변환하여 비교
                        const parseMonth = (monthStr) => {
                            const parts = monthStr.match(/(\d{4})년 (\d{1,2})월/);
                            return parseInt(parts[1] + (parts[2].length === 1 ? '0' : '') + parts[2]);
                        };
                        return parseMonth(a) - parseMonth(b);
                    });

                    const labels = sortedMonths;
                    const chartData = sortedMonths.map(month => monthlyData[month]);

                    // 2. 기존 차트가 있다면 파괴하여 메모리 누수 방지
                    if (myChart) {
                        myChart.destroy();
                    }

                    // 3. Chart.js 그래프 생성
                    myChart = new Chart(ctx, {
                        type: 'bar', // 막대 그래프
                        data: {
                            labels: labels, // X축: 월 (예: 2023년 1월, 2023년 2월)
                            datasets: [{
                                label: '월별 정산 금액 (원)', // 범례 라벨
                                data: chartData, // Y축: 월별 합산 금액
                                backgroundColor: 'rgba(75, 192, 192, 0.6)', // 막대 색상
                                borderColor: 'rgba(75, 192, 192, 1)', // 막대 테두리 색상
                                borderWidth: 1
                            }]
                        },
                        options: {
                            responsive: true, // 반응형
                            plugins: {
                                legend: {
                                    display: true // 범례 표시
                                },
                                tooltip: {
                                    callbacks: {
                                        label: function(context) {
                                            let label = context.dataset.label || '';
                                            if (label) {
                                                label += ': ';
                                            }
                                            if (context.parsed.y !== null) {
                                                label += new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(context.parsed.y);
                                            }
                                            return label;
                                        }
                                    }
                                }
                            },
                            scales: {
                                x: {
                                    title: {
                                        display: true,
                                        text: '월'
                                    }
                                },
                                y: {
                                    beginAtZero: true, // Y축 0부터 시작
                                    title: {
                                        display: true,
                                        text: '정산 금액 (원)'
                                    },
                                    ticks: {
                                        // Y축 값 포맷팅 (원 단위, 콤마)
                                        callback: function(value, index, values) {
                                            return new Intl.NumberFormat('ko-KR').format(value) + '원';
                                        }
                                    }
                                }
                            }
                        }
                    });
                } else {
                    // 데이터가 없는 경우 차트 초기화 또는 메시지 표시
                    if (myChart) {
                        myChart.destroy();
                    }
                    // 예시: 차트 캔버스에 직접 메시지 표시 (원한다면)
                    ctx.getContext('2d').clearRect(0, 0, ctx.width, ctx.height); // 캔버스 지우기
                    ctx.getContext('2d').font = '16px Arial';
                    ctx.getContext('2d').textAlign = 'center';
                    ctx.getContext('2d').fillText('월별 정산 데이터가 없습니다.', ctx.width / 2, ctx.height / 2);
                }
            })
            .catch(error => {
                console.error('Error fetching settlement data for chart:', error);
                if (myChart) {
                    myChart.destroy();
                }
                ctx.getContext('2d').clearRect(0, 0, ctx.width, ctx.height);
                ctx.getContext('2d').font = '16px Arial';
                ctx.getContext('2d').textAlign = 'center';
                ctx.getContext('2d').fillText('차트 데이터를 불러오는데 실패했습니다.', ctx.width / 2, ctx.height / 2);
            });
    }

    // 페이지 로드 시 차트 데이터 로드
    document.addEventListener('DOMContentLoaded', loadMonthlySettlementChart);
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



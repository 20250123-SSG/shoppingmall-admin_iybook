document.addEventListener('DOMContentLoaded', function() {
  document.querySelectorAll('.clickable-row').forEach(row => {
    row.addEventListener('click', () => {
      const date = row.dataset.date;
      const unit = row.dataset.unit;

      // 이미 열려있으면 접기
      if (row.classList.contains('expanded')) {
        document.querySelectorAll(`.child-of-${date}`).forEach(e => e.remove());
        row.classList.remove('expanded');
        return;
      }

      fetch(contextPath + `/statistics/detail.do?date=${date}&unit=${unit}`)
        .then(res => res.json())
        .then(data => {
          data.forEach(item => {
            const tr = document.createElement('tr');
            tr.classList.add(`child-of-${date}`, 'child-row');
            tr.innerHTML = `
            <td>↳ ${item.statisticsDate}</td>
            <td>₩${item.totalSales.toLocaleString()}</td>
            <td>${item.orderCount}</td>
            <td>${item.cancelCount}</td>
          `;
            row.after(tr);
          });
          row.classList.add('expanded');
        });
    });
  });

  document.getElementById('granularity').addEventListener('change', () => {
    const granularity = document.getElementById('granularity').value;

    const startContainer = document.getElementById('start-container');
    const endContainer = document.getElementById('end-container');

    if (granularity === 'YEAR') {
      startContainer.innerHTML = '';
      endContainer.innerHTML = '';
      startContainer.appendChild(populateYearSelect('start-date'));
      endContainer.appendChild(populateYearSelect('end-date'));
    } else {
      const type = granularity === 'MONTH' ? 'month' : 'date';

      startContainer.innerHTML = `<input type="${type}" id="start-date" name="startDate" required>`;
      endContainer.innerHTML = `<input type="${type}" id="end-date" name="endDate" required>`;
    }
  });


  document.getElementById('search-btn').addEventListener('click', () => {
    const granularity = document.getElementById('granularity').value;
    let startDate = document.getElementById('start-date').value;
    let endDate = document.getElementById('end-date').value;

    if (!startDate || !endDate) {
      alert("시작일과 종료일을 모두 선택해주세요.");
      return;
    }

    if (granularity === 'YEAR') {
      startDate = startDate + '-01-01';
      endDate = endDate + '-12-31';
    } else if (granularity === 'MONTH') {
      const getLastDayOfMonth = (ym) => {
        const [y, m] = ym.split('-');
        return new Date(y, m, 0).getDate();  // 말일 구하기
      };

      startDate = startDate + '-01';
      const endDay = getLastDayOfMonth(endDate);
      endDate = endDate + `-${endDay}`;
    }
    loadSalesChart(startDate, endDate, granularity);

    fetch(`${contextPath}/statistics/summary.do?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(data => renderSummaryTable(data, granularity));
  });

  function renderSummaryTable(data, granularity) {
    const tbody = document.getElementById('result-body');
    const summaryBox = document.getElementById('total-summary');
    tbody.innerHTML = '';
    summaryBox.innerHTML = '';

    if (data.length === 0) {
      tbody.innerHTML = '<tr><td colspan="4">데이터가 없습니다</td></tr>';
      return;
    }

    let totalSales = 0, totalOrder = 0, totalCancel = 0;

    data.forEach(stat => {
      totalSales += stat.totalSales;
      totalOrder += stat.orderCount;
      totalCancel += stat.cancelCount;

      const row = document.createElement('tr');
      row.classList.add('clickable-row');
      row.dataset.date = stat.statisticsDate;
      row.dataset.unit = granularity;

      row.innerHTML = `
      <td>${formatDateByGranularity(stat.statisticsDate, granularity)}</td>
      <td>₩${stat.totalSales.toLocaleString()}</td>
      <td>${stat.orderCount}건</td>
      <td>${stat.cancelCount}건</td>
    `;
      tbody.appendChild(row);
    });

    summaryBox.innerHTML = `
    총 매출: <strong>₩${totalSales.toLocaleString()}</strong> /
    주문: <strong>${totalOrder}건</strong> /
    취소: <strong>${totalCancel}건</strong>
  `;
  }

  function populateYearSelect(selectId, startYear = 2020, endYear = new Date().getFullYear()) {
    const select = document.createElement('select');
    select.id = selectId;
    select.name = selectId === 'start-date' ? 'startDate' : 'endDate';

    for (let y = endYear; y >= startYear; y--) {
      const option = new Option(y, y);
      select.add(option);
    }

    return select;
  }

  function formatDateByGranularity(dateStr, granularity) {
    const [y, m, d] = dateStr.split("-");
    if (granularity === "DAY") return `${y}-${m}-${d}`;
    if (granularity === "MONTH") return `${y}-${m}`;
    if (granularity === "YEAR") return `${y}`;
    return dateStr;
  }

  // 차트
  const ctx = document.getElementById('myChart');
  let myChart = null;

// 매출 통계 차트
  function loadSalesChart(startDate, endDate, granularity) {
    fetch(`${contextPath}/statistics/summary.do?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(response => response.json())
      .then(data => {
        if (data && data.length > 0) {
          const labels = data.map(item => item.statisticsDate);
          const chartData = data.map(item => item.totalSales);

          if (myChart) myChart.destroy();

          myChart = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: labels,
              datasets: [{
                label: '총 매출 금액 (원)',
                data: chartData,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
              }]
            },
            options: {
              responsive: true,
              plugins: {
                legend: { display: true },
                tooltip: {
                  callbacks: {
                    label: function(context) {
                      let label = context.dataset.label || '';
                      if (label) label += ': ';
                      if (context.parsed.y !== null) {
                        label += new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(context.parsed.y);
                      }
                      return label;
                    }
                  }
                }
              },
              scales: {
                x: { title: { display: true, text: '' } },
                y: {
                  beginAtZero: true,
                  title: { display: true, text: '금액 (원)' },
                  ticks: {
                    callback: function(value) {
                      return new Intl.NumberFormat('ko-KR').format(value) + '원';
                    }
                  }
                }
              }
            }
          });
        } else {
          if (myChart) myChart.destroy();
          const ctx2d = ctx.getContext('2d');
          ctx2d.clearRect(0, 0, ctx.width, ctx.height);
          ctx2d.font = '16px Arial';
          ctx2d.textAlign = 'center';
          ctx2d.fillText('통계 데이터가 없습니다.', ctx.width / 2, ctx.height / 2);
        }
      })
      .catch(error => {
        console.error('Error fetching settlement data for chart:', error);
        if (myChart) myChart.destroy();
        const ctx2d = ctx.getContext('2d');
        ctx2d.clearRect(0, 0, ctx.width, ctx.height);
        ctx2d.font = '16px Arial';
        ctx2d.textAlign = 'center';
        ctx2d.fillText('차트 데이터를 불러오는데 실패했습니다.', ctx.width / 2, ctx.height / 2);
      });
  }
    document.addEventListener('DOMContentLoaded', loadMonthlySettlementChart);

});

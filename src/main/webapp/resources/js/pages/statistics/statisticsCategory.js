document.addEventListener('DOMContentLoaded', function () {

  // 조회 버튼 이벤트
  document.getElementById('search-btn').addEventListener('click', () => {
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;
    const granularity = document.getElementById('granularity').value;

    if (!startDate || !endDate) {
      alert("시작일과 종료일을 선택해주세요.");
      return;
    }

    fetch(`${contextPath}/statistics/category/all?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(data => {
        renderCategoryTable(data);
        loadCategoryChart(data);
      });

    fetch(`${contextPath}/statistics/category/gender?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(data => renderTable(data, 'gender-body'));

    fetch(`${contextPath}/statistics/category/age?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(data => renderTable(data, 'age-body'));
  });

  // 전체 카테고리 차트
  let categoryChart = null;

  function loadCategoryChart(data) {
    const ctx = document.getElementById('categoryChart');
    const labels = data.map(item => item.categoryName);
    const values = data.map(item => item.orderCount);

    if (categoryChart) categoryChart.destroy();

    categoryChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [{
          label: '카테고리별 판매량',
          data: values,
          backgroundColor: 'rgba(255, 99, 132, 0.6)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: function(context) {
                return `${context.parsed.y}건`;
              }
            }
          }
        },
        scales: {
          x: { title: { display: true, text: '카테고리' } },
          y: {
            beginAtZero: true,
            title: { display: true, text: '판매 건수' }
          }
        }
      }
    });
  }

  // 전체 카테고리 테이블
  function renderCategoryTable(data) {
    const tbody = document.getElementById('all-body');
    tbody.innerHTML = '';
    data.forEach(item => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${item.categoryName}</td>
        <td>${item.orderCount}건</td>
      `;
      tbody.appendChild(tr);
    });
  }

  // 성별/나이대용 테이블
  function renderTable(data, tbodyId) {
    const tbody = document.getElementById(tbodyId);
    tbody.innerHTML = '';
    data.forEach(row => {
      const tr = document.createElement('tr');
      if (row.gender) {
        tr.innerHTML = `<td>${row.gender}</td><td>${row.categoryName}</td><td>${row.orderCount}</td>`;
      } else if (row.ageGroup) {
        tr.innerHTML = `<td>${row.ageGroup}</td><td>${row.categoryName}</td><td>${row.orderCount}</td>`;
      }
      tbody.appendChild(tr);
    });
  }
});

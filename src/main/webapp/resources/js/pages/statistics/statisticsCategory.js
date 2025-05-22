document.addEventListener('DOMContentLoaded', function () {
  let chartAll = null;

  const colorPalette = [
    '#4E79A7', '#F28E2B', '#E15759', '#76B7B2',
    '#59A14F', '#EDC948', '#B07AA1', '#FF9DA7',
    '#9C755F', '#BAB0AC'
  ];

  // 한글 그룹명을 안전한 ID로 매핑
  const GROUP_KEY_MAP = {
    '남': 'male',
    '여': 'female',
    '10대': 'age10',
    '20대': 'age20',
    '30대': 'age30',
    '40대': 'age40',
    '50대 이상': 'age50',
    '기타': 'etc'
  };

  function groupBy(array, key) {
    return array.reduce((acc, item) => {
      const group = item[key];
      if (!acc[group]) acc[group] = [];
      acc[group].push(item);
      return acc;
    }, {});
  }

  function drawDonutChart(canvasId, data) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) {
      console.warn('canvas element not found:', canvasId);
      return;
    }
    console.log(data)
    const labels = data.map(item => item.categoryName);
    const values = data.map(item => item.orderCount);
    const colors = labels.map((_, idx) => colorPalette[idx % colorPalette.length]);
    console.log(values)
    const chart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels,
        datasets: [{
          data: values,
          backgroundColor: colors,
          borderColor: 'white',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: ctx => `${ctx.label}: ${ctx.parsed}건`
            }
          }
        }
      }
    });
  }

  function loadCategoryChartAll(data) {
    const ctx = document.getElementById('categoryChart');
    const labels = data.map(item => item.categoryName);
    const values = data.map(item => item.orderCount);
    const backgroundColors = labels.map((_, idx) => colorPalette[idx % colorPalette.length]);

    if (chartAll) chartAll.destroy();

    chartAll = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels,
        datasets: [{
          data: values,
          backgroundColor: backgroundColors,
          borderColor: 'white',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: ctx => `${ctx.label}: ${ctx.parsed}건`
            }
          }
        }
      }
    });
  }

  function loadCategoryChartGender(data) {
    const container = document.getElementById('gender-chart-container');
    container.innerHTML = ''; // 기존 차트 초기화

    const grouped = groupBy(data, 'gender'); // 먼저 그룹화

    for (const gender in grouped) {
      const safeKey = GROUP_KEY_MAP[gender] || encodeURIComponent(gender);
      const chartId = `chart-gender-${safeKey}`;

      const box = document.createElement('div');
      box.classList.add('chart-box');

      const title = document.createElement('h4');
      title.textContent = gender;

      const canvas = document.createElement('canvas');
      canvas.id = chartId;

      box.appendChild(title);
      box.appendChild(canvas);
      container.appendChild(box);

      drawDonutChart(chartId, grouped[gender]);
    }
  }

  function loadCategoryChartAge(data) {

    const container = document.getElementById('age-chart-container');
    container.innerHTML = ''; // 기존 차트 초기화

    const grouped = groupBy(data, 'ageGroup'); // 먼저 그룹화

    for (const ageGroup in grouped) {
      const safeKey = GROUP_KEY_MAP[ageGroup] || encodeURIComponent(ageGroup);
      const chartId = `chart-age-${safeKey}`;

      const box = document.createElement('div');
      box.classList.add('chart-box');

      const title = document.createElement('h4');
      title.textContent = ageGroup;

      const canvas = document.createElement('canvas');
      canvas.id = chartId;

      box.appendChild(title);
      box.appendChild(canvas);
      container.appendChild(box);

      drawDonutChart(chartId, grouped[ageGroup]);
    }
  }

  function fetchAndRenderCharts() {
    const startDate = document.getElementById('start-date')?.value?.slice(0, 7);
    const endDate = document.getElementById('end-date')?.value?.slice(0, 7);
    const granularity = 'MONTH';

    if (!startDate || !endDate) {
      alert("시작일과 종료일을 선택해주세요.");
      return;
    }

    fetch(`${contextPath}/statistics/category/all?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(loadCategoryChartAll);

    fetch(`${contextPath}/statistics/category/gender?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(loadCategoryChartGender);

    fetch(`${contextPath}/statistics/category/age?startDate=${startDate}&endDate=${endDate}&granularity=${granularity}`)
      .then(res => res.json())
      .then(loadCategoryChartAge);
  }

  document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');

      document.querySelectorAll('.tab-content').forEach(tab => tab.style.display = 'none');
      const target = btn.dataset.tab;
      document.getElementById(target).style.display = 'block';
    });
  });

  document.getElementById('search-btn').addEventListener('click', fetchAndRenderCharts);

  const today = new Date();
  const yyyyMM = today.toISOString().slice(0, 7);
  document.getElementById('start-date').value = yyyyMM;
  document.getElementById('end-date').value = yyyyMM;

  fetchAndRenderCharts();
});

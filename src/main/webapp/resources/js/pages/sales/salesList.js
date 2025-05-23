window.addEventListener("DOMContentLoaded", (evt) => {
  const startInput = document.getElementById("startDate");
  const endInput = document.getElementById("endDate");
  const buttons = document.querySelectorAll(".date-btn");

  const formatDate = (d) => {
    const yyyy = d.getFullYear();
    const mm = String(d.getMonth() + 1).padStart(2, "0");
    const dd = String(d.getDate()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}`;
  };

  const setPeriod = (daysAgo, clickedBtn = null) => {
    const today = new Date();
    const start = new Date();
    start.setDate(today.getDate() - daysAgo);

    startInput.value = formatDate(start);
    endInput.value = formatDate(today);

    buttons.forEach(btn => btn.classList.remove("btn-active"));
    if (clickedBtn) {
      clickedBtn.classList.add("btn-active");
    }
  };

  const activateButtonIfMatchingDates = () => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const start = new Date(startInput.value);
    const end = new Date(endInput.value);
    start.setHours(0, 0, 0, 0);
    end.setHours(0, 0, 0, 0);

    const diff = Math.round((today - start) / (1000 * 60 * 60 * 24));
    const map = {
      0: "todayBtn",
      7: "weekBtn",
      30: "monthBtn",
      90: "threeMonthBtn",
      730: "twoYearBtn"
    };

    buttons.forEach(btn => btn.classList.remove("btn-active"));
    if (map[diff] && end.getTime() === today.getTime()) {
      const btn = document.getElementById(map[diff]);
      if (btn) btn.classList.add("btn-active");
    }
  };

  const validateDateRange = () => {
    const start = new Date(startInput.value);
    const end = new Date(endInput.value);
    const today = new Date();
    start.setHours(0, 0, 0, 0);
    end.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0);

    const twoYearsAgo = new Date();
    twoYearsAgo.setDate(today.getDate() - 730);
    twoYearsAgo.setHours(0, 0, 0, 0);

    if (start > end) {
      alert("시작일과 종료일을 확인해 주세요.");
      return false;
    }
    if (start < twoYearsAgo || start > today) {
      alert("최대 조회 기간은 2년입니다.");
      return false;
    }
    if (end < twoYearsAgo || end > today) {
      alert("종료일을 확인해주세요.");
      return false;
    }
    return true;
  };

  activateButtonIfMatchingDates();

  startInput.addEventListener("change", activateButtonIfMatchingDates);
  endInput.addEventListener("change", activateButtonIfMatchingDates);

  document.getElementById("todayBtn").addEventListener("click", (evt) => {
    evt.preventDefault();
    setPeriod(0, evt.target);
  });
  document.getElementById("weekBtn").addEventListener("click", (evt) => {
    evt.preventDefault();
    setPeriod(7, evt.target);
  });
  document.getElementById("monthBtn").addEventListener("click", (evt) => {
    evt.preventDefault();
    setPeriod(30, evt.target);
  });
  document.getElementById("threeMonthBtn").addEventListener("click", (evt) => {
    evt.preventDefault();
    setPeriod(90, evt.target);
  });
  document.getElementById("twoYearBtn").addEventListener("click", (evt) => {
    evt.preventDefault();
    setPeriod(730, evt.target);
  });

  // 주문 상태 전체 선택 제어
  const checkAllStatus = document.getElementById("checkAllStatus");
  const statusCheckboxes = document.querySelectorAll("input[name='orderStatus']");
  const rawStatusStr = document.getElementById("filterOrderStatus")?.value || "";
  const selectedStatuses = rawStatusStr.replace(/^\[|\]$/g, "").split(",").map(s => s.trim()).filter(Boolean);

  statusCheckboxes.forEach(cb => {
    cb.checked = selectedStatuses.includes(cb.value);
  });

  checkAllStatus.checked = Array.from(statusCheckboxes).every(cb => cb.checked);

  checkAllStatus?.addEventListener("change", () => {
    statusCheckboxes.forEach(cb => cb.checked = checkAllStatus.checked);
  });

  statusCheckboxes.forEach(cb => {
    cb.addEventListener("change", () => {
      checkAllStatus.checked = Array.from(statusCheckboxes).every(cb => cb.checked);
    });
  });

  document.getElementById("search-form")?.addEventListener("submit", (evt) => {
    if (!validateDateRange()) {
      evt.preventDefault();
    }
  });
});

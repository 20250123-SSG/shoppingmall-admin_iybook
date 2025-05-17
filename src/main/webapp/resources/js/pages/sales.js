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

  const validateDateRange = () => {
    const start = new Date(startInput.value);
    const end = new Date(endInput.value);
    const today = new Date();

    today.setHours(0, 0, 0, 0);

    const twoYearsAgo = new Date();
    twoYearsAgo.setDate(today.getDate() - 730);
    twoYearsAgo.setHours(0, 0, 0, 0);

    if (start > end) {
      alert("시작작일과 종료일을 확인해 주세요.");
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

  setPeriod(7);
  document.getElementById("weekBtn").classList.add("btn-active");

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

  startInput.addEventListener("change", (evt) => {
    validateDateRange();
    buttons.forEach(btn => btn.classList.remove("btn-active"));
  });

  endInput.addEventListener("change", (evt) => {
    validateDateRange();
    buttons.forEach(btn => btn.classList.remove("btn-active"));
  });


  const checkAll = document.getElementById("checkAllStatus");
  const checkboxes = document.querySelectorAll("input[name='orderStatus']");

  if (checkAll && checkboxes.length > 0) {
    checkAll.addEventListener("change", (evt) => {
      checkboxes.forEach(cb => cb.checked = checkAll.checked);
    });
  }

  checkboxes.forEach(cb => {
    cb.addEventListener("change", (evt) => {
      const allChecked = Array.from(checkboxes).every(cb => cb.checked);
      checkAll.checked = allChecked;
    });
  });
});



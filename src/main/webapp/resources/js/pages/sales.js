window.addEventListener("DOMContentLoaded", (evt) => {
  const SELECTED_KEY = "selectedOrderIds";
  const getStoredIds = () => JSON.parse(localStorage.getItem(SELECTED_KEY)) || [];
  const saveStoredIds = (ids) => localStorage.setItem(SELECTED_KEY, JSON.stringify(ids));
  const updateStorage = (orderId, checked) => {
    const current = getStoredIds();
    if (checked) {
      if (!current.includes(orderId)) current.push(orderId);
    } else {
      const index = current.indexOf(orderId);
      if (index !== -1) current.splice(index, 1);
    }
    saveStoredIds(current);
  };

  const urlParams = new URLSearchParams(window.location.search);
  const isFirstPage = !urlParams.has("page");
  if (isFirstPage) {
    localStorage.removeItem(SELECTED_KEY);
  }

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
      document.getElementById(map[diff]).classList.add("btn-active");
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

  // 주문 상태 체크박스 (orderStatus)
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

  // 체크박스 초기화
  const rowCheckboxes = document.querySelectorAll("input[name='orderCheckbox']");
  rowCheckboxes.forEach(cb => {
    if (getStoredIds().includes(cb.value)) {
      cb.checked = true;
    }

    cb.addEventListener("change", (evt) => {
      updateStorage(cb.value, evt.target.checked);
      syncCheckAllBox();
    });
  });

  // 전체선택 체크박스
  const checkAllBox = document.getElementById("checkAll");

  const syncCheckAllBox = () => {
    const allChecked = Array.from(document.querySelectorAll("input[name='orderCheckbox']")).every(cb => cb.checked);
    if (checkAllBox) checkAllBox.checked = allChecked;
  };

  checkAllBox?.addEventListener("change", async (evt) => {
    const checked = evt.target.checked;

    if (checked) {
      try {
        const formData = new FormData(document.getElementById("search-form"));
        const params = new URLSearchParams(formData);
        const res = await fetch(`${contextPath}/sales/allOrderIds.ajax?` + params.toString());
        const data = await res.json();
        if (!Array.isArray(data.ids)) {
          alert("주문 ID를 불러오지 못했습니다.");
          return;
        }

        saveStoredIds(data.ids);
        document.querySelectorAll("input[name='orderCheckbox']").forEach(cb => cb.checked = true);
      } catch (err) {
        console.error("fetch 실패:", err);
        alert("전체 선택 실패: 서버 오류");
      }
    } else {
      getStoredIds().forEach(id => updateStorage(id, false));
      document.querySelectorAll("input[name='orderCheckbox']").forEach(cb => cb.checked = false);
    }
  });

  // 행 클릭 시 토글
  document.querySelectorAll(".selectable-row").forEach(row => {
    row.addEventListener("click", (evt) => {
      if (evt.target.tagName.toLowerCase() === "input") return;
      const checkbox = row.querySelector("input[name='orderCheckbox']");
      if (!checkbox) return;
      checkbox.checked = !checkbox.checked;
      updateStorage(checkbox.value, checkbox.checked);
      syncCheckAllBox();
    });
  });

  // 검색 폼 제출
  document.getElementById("search-form")?.addEventListener("submit", (evt) => {
    if (!validateDateRange()) {
      evt.preventDefault();
      return;
    }
    localStorage.removeItem(SELECTED_KEY);
  });

  // 주문 수락/취소 버튼 눌렀을 때 hidden에 값 넣기
  document.getElementById("order-action-form")?.addEventListener("submit", () => {
    const storedIds = getStoredIds();
    document.getElementById("selectedOrderIds").value = storedIds.join(",");
    localStorage.removeItem(SELECTED_KEY);
  });
});

document.addEventListener("DOMContentLoaded", function () {
  // ✅ [판매상태 체크박스 동기화]
  const statusAll = document.querySelector('input[id="all"]');
  const statusOptions = Array.from(document.querySelectorAll('input[name="status"]'))
    .filter(cb => cb.id !== "all");

  function updateAllCheckbox() {
    const allChecked = statusOptions.every(checkbox => checkbox.checked);
    statusAll.checked = allChecked;
  }

  statusAll.addEventListener("change", function () {
    statusOptions.forEach(cb => cb.checked = statusAll.checked);
  });

  statusOptions.forEach(cb => {
    cb.addEventListener("change", updateAllCheckbox);
  });

  updateAllCheckbox(); // ✅ 초기 상태 동기화

  // ✅ [날짜 단축 버튼 및 유효성 체크]
  const startInput = document.querySelector('input[name="startDate"]');
  const endInput = document.querySelector('input[name="endDate"]');
  const shortcutButtons = document.querySelectorAll(".date-shortcuts button");

  shortcutButtons.forEach(button => {
    button.addEventListener("click", function () {
      const days = parseInt(button.dataset.range);
      const today = new Date();
      const startDate = new Date(today);
      startDate.setDate(today.getDate() - days + 1);

      startInput.value = startDate.toISOString().split('T')[0];
      endInput.value = today.toISOString().split('T')[0];

      shortcutButtons.forEach(btn => btn.classList.remove("active"));
      button.classList.add("active");

      updateDateConstraints(); // ✅ 버튼 클릭 시도 유효범위 갱신
    });
  });

  [startInput, endInput].forEach(input => {
    input.addEventListener("change", () => {
      shortcutButtons.forEach(btn => btn.classList.remove("active"));
      updateDateConstraints(); // ✅ 직접 입력 시도 유효범위 갱신
    });
  });

  // 날짜 유효성 검사
  endInput.addEventListener("change", () => {
    if (startInput.value && endInput.value && startInput.value > endInput.value) {
      alert("종료일은 시작일보다 빠를 수 없습니다.");
      endInput.value = startInput.value;
    }
  });

  startInput.addEventListener("change", () => {
    if (startInput.value && endInput.value && startInput.value > endInput.value) {
      alert("시작일은 종료일보다 늦을 수 없습니다.");
      startInput.value = endInput.value;
    }
  });

  function updateDateConstraints() {
    if (startInput.value) {
      endInput.min = startInput.value;
    }
    if (endInput.value) {
      startInput.max = endInput.value;
    }
  }


    // 시작/종료일이 있어야 판단 가능
    if (startInput.value && endInput.value) {
      const today = new Date();
      const endDate = new Date(endInput.value);
      const startDate = new Date(startInput.value);

      // 날짜 차이 계산
      const diffTime = endDate.getTime() - startDate.getTime();
      const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)) + 1;

      // 오늘 기준인지 확인
      const isEndToday = endDate.toISOString().split("T")[0] === today.toISOString().split("T")[0];

      // 버튼 중 해당하는 범위 찾기
      shortcutButtons.forEach(button => {
        const range = parseInt(button.dataset.range);
        if (isEndToday && range === diffDays) {
          button.classList.add("active");
        } else {
          button.classList.remove("active");
        }
      });
    }

  updateDateConstraints(); // ✅ 페이지 로딩 시 기본 유효범위 적용


});

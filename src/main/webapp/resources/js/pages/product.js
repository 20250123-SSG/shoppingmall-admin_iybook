
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

// table 체크박스 선택 구문
document.addEventListener("DOMContentLoaded", function () {
  const masterCheckbox = document.querySelector('.product-table thead input[type="checkbox"]');
  const rowCheckboxes = document.querySelectorAll('.product-table tbody input[type="checkbox"]');

  // 마스터 체크박스 클릭 시
  masterCheckbox.addEventListener('change', function () {
    rowCheckboxes.forEach(cb => cb.checked = masterCheckbox.checked);
  });

  // 개별 체크박스 변경 시 마스터 체크박스 상태 업데이트
  rowCheckboxes.forEach(cb => {
    cb.addEventListener('change', function () {
      const allChecked = Array.from(rowCheckboxes).every(cb => cb.checked);
      masterCheckbox.checked = allChecked;
    });
  });
});


/* 일괄처리 */
document.addEventListener("DOMContentLoaded", function () {
  const deleteBtn = document.getElementById("deleteSelected");
  const saveBtn = document.getElementById("saveChanges");
  const statusSelect = document.getElementById("statusChangeSelect");

  // 선택된 체크박스들의 도서 ID 목록을 배열로 반환
  function getSelectedBookIds() {
    return Array.from(document.querySelectorAll('.product-table tbody input[type="checkbox"]:checked'))
        .map(cb => cb.value);
  }

  // 선택 삭제 버튼 클릭
  deleteBtn.addEventListener("click", function () {
    const selectedIds = getSelectedBookIds();
    if (selectedIds.length === 0) {
      alert("삭제할 항목을 선택하세요.");
      return;
    }

    if (!confirm("정말 삭제하시겠습니까?")) return;

    // AJAX 요청
    fetch(contextPath + '/product/delete.do', {
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({bookIds: selectedIds})
    })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            alert(data.deleteCount + "건 삭제가 완료되었습니다.");
          } else {
            alert("삭제 실패")
          }
          location.reload();
        })
        .catch(err => {
          alert("삭제 중 오류 발생");
        });
  });

  // 판매상태 저장 버튼 클릭
  saveBtn.addEventListener("click", function () {
    const selectedIds = getSelectedBookIds();
    const newStatus = statusSelect.value;

    if (selectedIds.length === 0) {
      alert("수정할 항목을 선택하세요.");
      return;
    }
    if (!newStatus) {
      alert("변경할 상태를 선택하세요.");
      return;
    }

    // AJAX 요청
    fetch("/book/updateStatus", {
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({bookIds: selectedIds, status: newStatus})
    })
        .then(res => res.json())
        .then(data => {
          alert("판매상태가 변경되었습니다.");
          location.reload();
        })
        .catch(err => {
          alert("상태 변경 중 오류 발생");
          console.error(err);
        });
  });
});

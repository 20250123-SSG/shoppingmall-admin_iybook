
document.addEventListener("DOMContentLoaded", function () {
  // 판매상태 체크박스 동기화
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

  updateAllCheckbox();

  // 날짜 단축 버튼 및 유효성 체크
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

      updateDateConstraints();
    });
  });

  [startInput, endInput].forEach(input => {
    input.addEventListener("change", () => {
      shortcutButtons.forEach(btn => btn.classList.remove("active"));
      updateDateConstraints();
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

  updateDateConstraints();


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
      alert("판매종료 처리할 항목을 선택하세요.");
      return;
    }

    if (!confirm("정말 변경하시겠습니까?")) return;

    // AJAX 요청
    fetch(contextPath + '/product/update.do', {
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({bookIds: selectedIds, status: '숨김'})
    })
        .then(response => response.json())
        .then(data => {
          if (data.success) {
            alert(data.resultCount + "건 판매종료 처리되었습니다.");
          } else {
            alert("설정 변경 실패")
          }
          location.reload();
        })
        .catch(err => {
          alert("판매종료 처리 중 오류 발생");
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
    fetch(contextPath + "/product/update.do", {
      method: "POST",
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({bookIds: selectedIds, status: newStatus})
    })
        .then(res => res.json())
        .then(data => {
          if (data.success) {
            alert(data.resultCount + "건 판매상태가 변경되었습니다.");
          } else {
            alert("변경 실패")
          }
          location.reload();
        })
        .catch(err => {
          alert("상태 변경 중 오류 발생");
          console.error(err);
        });
  });
});

/* 상세보기(수정) 페이지 돌입 */
document.addEventListener("DOMContentLoaded", function () {
  const rows = document.querySelectorAll(".product-table tbody tr");

  rows.forEach(row => {
    const checkbox = row.querySelector("td:first-child input[type='checkbox']");

    row.addEventListener("click", function (e) {
      // 체크박스를 클릭했으면 row 이동 막기
      if (e.target === checkbox) return;

      const bookId = row.dataset.bookId;
      if (bookId) {
        window.location.href = contextPath + "/product/update.page?bookId=" + bookId;
      }
    });
  });
});

function loadBookList(page = 1) {
  const form = document.getElementById('searchForm');
  const formData = new FormData(form);

  // page 값을 추가해줍니다
  formData.append("page", page);

  // FormData를 URL 파라미터로 변환
  const params = new URLSearchParams(formData).toString();

  fetch(`${contextPath}/product/list.page?${params}`)
    .then(response => response.text())
    .then(html => {
      const parser = new DOMParser();
      const doc = parser.parseFromString(html, 'text/html');

      const newTable = doc.querySelector('#productTableBody');
      const newPagination = doc.querySelector('#pagination');

      if (newTable && newPagination) {
        document.getElementById('productTableBody').innerHTML = newTable.innerHTML;
        document.getElementById('pagination').innerHTML = newPagination.innerHTML;

        const tableOffset = document.querySelector('.product-table')?.offsetTop || 0;
        window.scrollTo({ top: tableOffset, behavior: 'smooth' });
      } else {
        console.warn("AJAX 응답에 필요한 요소가 없습니다.");
      }
    });
}

// 초기 로딩 및 이벤트 바인딩
document.addEventListener('DOMContentLoaded', () => {
  // 페이지 로드시 첫 데이터 로딩
  loadBookList(1);

  // 이벤트 위임 방식으로 페이지네이션 링크 감지
  document.addEventListener('click', function (e) {
    const target = e.target;
    if (target.matches('.pagination a[data-page]')) {
      e.preventDefault();
      const page = target.getAttribute('data-page');
      loadBookList(page);
    }
  });
});




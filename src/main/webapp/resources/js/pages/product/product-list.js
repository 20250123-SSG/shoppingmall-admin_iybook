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

    const diffTime = endDate.getTime() - startDate.getTime();
    const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)) + 1;

    const isEndToday = endDate.toISOString().split("T")[0] === today.toISOString().split("T")[0];

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

// table 체크박스 선택 구문 (이벤트 위임)
document.addEventListener("DOMContentLoaded", function () {
  const table = document.querySelector('.product-table');
  const masterCheckbox = table.querySelector('thead input[type="checkbox"]');
  const tableBody = table.querySelector('tbody');

  // 마스터 체크박스 클릭 시
  masterCheckbox.addEventListener('change', function () {
    const checkboxes = tableBody.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(cb => cb.checked = masterCheckbox.checked);
  });

  // tbody에 이벤트 위임해서 개별 체크박스 변경 감지
  tableBody.addEventListener("change", function (evt) {
    if (evt.target.matches('input[type="checkbox"]')) {
      const all = tableBody.querySelectorAll('input[type="checkbox"]');
      const checked = tableBody.querySelectorAll('input[type="checkbox"]:checked');
      masterCheckbox.checked = all.length > 0 && all.length === checked.length;
    }
  });
});


/* 일괄처리 */
document.addEventListener("DOMContentLoaded", function () {
  const deleteBtn = document.getElementById("deleteSelected");
  const saveBtn = document.getElementById("saveChanges");
  const statusSelect = document.getElementById("statusChangeSelect");

  function getSelectedBookIds() {
    return Array.from(document.querySelectorAll('.product-table tbody input[type="checkbox"]:checked'))
      .map(cb => cb.value);
  }

  deleteBtn.addEventListener("click", function () {
    const selectedIds = getSelectedBookIds();
    if (selectedIds.length === 0) {
      alert("판매종료 처리할 항목을 선택하세요.");
      return;
    }

    if (!confirm("정말 변경하시겠습니까?")) return;

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

/* ✅ 상세보기(수정) 페이지 돌입 - 이벤트 위임 방식으로 변경됨 */
document.addEventListener("DOMContentLoaded", function () {
  const tableBody = document.querySelector(".product-table tbody");

  if (!tableBody) return;

  tableBody.addEventListener("click", function (evt) {
    const row = evt.target.closest("tr");
    if (!row || !row.dataset.bookId) return;

    if (evt.target.closest('input[type="checkbox"]')) return;

    const bookId = row.dataset.bookId;
    if (bookId) {
      window.location.href = `${contextPath}/product/update.page?bookId=${bookId}`;
    }
  });
});

function loadBookList(page = 1) {
  const form = document.getElementById('searchForm');
  const formData = new FormData(form);

  formData.append("page", page);

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

document.addEventListener('DOMContentLoaded', () => {
  loadBookList(1);

  document.addEventListener('click', function (e) {
    const target = e.target;
    if (target.matches('.pagination a[data-page]')) {
      e.preventDefault();
      const page = target.getAttribute('data-page');
      loadBookList(page);
    }
  });
});

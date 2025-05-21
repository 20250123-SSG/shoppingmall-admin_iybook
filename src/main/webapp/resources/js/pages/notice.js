function toggleStatus(noticeId) {
  if (!confirm("상태를 변경하시겠습니까?")) return;

  fetch('${contextPath}/notice/toggleStatus.do', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'X-Requested-With': 'XMLHttpRequest' // AJAX 요청임을 서버에 알림
    },
    body: `noticeId=${noticeId}`
  })
    .then(response => response.text())
    .then(message => {
      alert(message);
      location.reload(); // 상태 변경 후 새로고침 (필요 시)
    })
    .catch(() => alert('상태 변경 중 오류가 발생했습니다.'));
}
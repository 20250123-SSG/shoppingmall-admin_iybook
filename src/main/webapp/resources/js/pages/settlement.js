document.addEventListener('DOMContentLoaded', function() {
    // 현재 날짜를 가져와서 month input의 기본값으로 설정
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const defaultValue = `${year}-${month}`;
    
    const settlementMonth = document.getElementById('settlementMonth');
    
    // 파라미터로 전달된 값이 없는 경우 현재 월을 기본값으로 설정
    if (!settlementMonth.value) {
        settlementMonth.value = defaultValue;
    }
    
    // 폼 제출 전 유효성 검사
    document.querySelector('form').addEventListener('submit', function(e) {
        if (!settlementMonth.value) {
            e.preventDefault();
            alert('정산월을 선택해주세요.');
            return false;
        }
    });
});

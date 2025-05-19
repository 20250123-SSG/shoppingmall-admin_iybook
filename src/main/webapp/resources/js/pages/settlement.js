document.addEventListener('DOMContentLoaded', function() {
    // 현재 날짜를 가져와서 month input의 기본값으로 설정
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const defaultValue = `${year}-${month}`;
    
    const settlementMonth = document.getElementById('settlementMonth');
    
    if (!settlementMonth.value) {
        settlementMonth.value = defaultValue;
    }
});

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<style>
    .tab-box {
        margin: 20px 0;
    }

    .tab-box ul {
        display: flex;
        list-style-type: none;
        padding-left: 0;
        margin: 0;
        border-bottom: 2px solid #e9ecef;
    }

    .tab-box li {
        padding: 12px 24px;
        margin-right: 4px;
        cursor: pointer;
        font-size: 15px;
        color: #495057;
        background: transparent;
        border: none;
        position: relative;
        transition: all 0.2s ease;
        list-style: none;
    }

    .tab-box li:hover {
        color: #495057;
    }

    .tab-box li.selected {
        color: #495057;
        font-weight: 600;
    }

    .tab-box li.selected::after {
        content: '';
        position: absolute;
        bottom: -2px;
        left: 0;
        width: 100%;
        height: 2px;
        background-color: #495057;
    }

    .tab-view {
        display: none;
        padding: 24px 0;
    }

    .tab-view.selected {
        display: block;
    }

    .search-area {
        background: #f8f9fa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 20px;
    }

    .table th {
        background: #f8f9fa;
        font-weight: 600;
        color: #495057;
    }

    .table td {
        color: #495057;
    }

    .container-fluid {
        padding: 24px;
    }
</style>

<div class="main">
    <div class="container-fluid">
        <div class="tab-box">
            <ul class="clearfix">
                <li class="selected" data-tab="monthly">월별 정산내역</li>
                <li data-tab="detail">건별 정산내역</li>
            </ul>
            
            <div id="settlementViewContainer">
                <!-- 초기에는 월별 정산내역을 보여줌 -->
                <jsp:include page="monthlySettlement.jsp"/>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const tabs = document.querySelectorAll('.tab-box li');
    const viewContainer = document.getElementById('settlementViewContainer');

    function loadView(tabId) {
        fetch('${contextPath}/settlement/' + tabId)
            .then(response => response.text())
            .then(html => {
                viewContainer.innerHTML = html;
            })
            .catch(error => {
                console.error('Error loading view:', error);
                viewContainer.innerHTML = '<p>뷰를 로드하는 데 실패했습니다.</p>';
            });
    }

    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            tabs.forEach(t => t.classList.remove('selected'));
            this.classList.add('selected');
            const tabId = this.getAttribute('data-tab');
            
            if (tabId === 'monthly') {
                viewContainer.innerHTML = '<jsp:include page="monthlySettlement.jsp"/>';
            } else if (tabId === 'detail') {
                viewContainer.innerHTML = '<jsp:include page="detailSettlement.jsp"/>';
            }
        });
    });
});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



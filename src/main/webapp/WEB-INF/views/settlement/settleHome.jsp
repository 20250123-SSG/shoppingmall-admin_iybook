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

    .tab-view.active {
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
            <ul>
                <li class="selected" data-view="monthlyView">월별 정산내역</li>
                <li data-view="detailView">정산내역 목록</li>
            </ul>
            
            <div class="tab-view active" id="monthlyView">
                <jsp:include page="monthlySettlement.jsp"/>
            </div>

            <div class="tab-view" id="detailView">
                <jsp:include page="settlementList.jsp"/>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const tabItems = document.querySelectorAll('.tab-box ul li');
    const tabViews = document.querySelectorAll('.tab-view');

    tabItems.forEach(tab => {
        tab.addEventListener('click', () => {
            // 1. 모든 탭 메뉴에서 'selected' 클래스 제거
            tabItems.forEach(item => item.classList.remove('selected'));

            // 2. 클릭된 탭 메뉴에 'selected' 클래스 추가
            tab.classList.add('selected');

            // 3. 모든 탭 뷰에서 'active' 클래스 제거
            tabViews.forEach(view => view.classList.remove('active'));

            // 4. 클릭된 탭 메뉴의 'data-view' 속성 값 가져오기
            const viewId = tab.dataset.view;

            // 5. 해당 'id'를 가진 탭 뷰에 'active' 클래스 추가하여 표시
            const selectedView = document.getElementById(viewId);
            if (selectedView) {
                selectedView.classList.add('active');
            }
        });
    });
});
</script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
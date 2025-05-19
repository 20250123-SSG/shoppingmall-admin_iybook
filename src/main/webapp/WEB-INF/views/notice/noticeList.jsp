<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/notice/notice.css">

<div class="main">

<div class="notice-container">
    <h2>공지사항</h2>
    <table class="notice-table">
        <thead>
        <tr>
            <th>No</th>
            <th>제목</th>
            <th>내용</th>
            <th>생성일</th>
            <th>수정일</th>
            <th>숨김 여부</th>
            <th>상태 변경</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="notice" items="${list}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td>
                    <a href="noticeDetail.do?noticeId=${notice.noticeId}">
                            ${notice.title}
                    </a>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${fn:length(notice.description) > 30}">
                            ${fn:substring(notice.description, 0, 30)}...
                        </c:when>
                        <c:otherwise>
                            ${notice.description}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${notice.createdAt}</td>
                <td>${notice.updatedAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${notice.publishStatus == '숨김'}">숨김</c:when>
                        <c:otherwise>게시중</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form method="post" action="${contextPath}/notice/toggleStatus.do" style="margin:0;">
                        <input type="hidden" name="noticeId" value="${notice.noticeId}">
                        <input type="hidden" name="currentHidden" value="${notice.publishStatus == '숨김'}"/>
                        <button type="submit">
                            <c:choose>
                                <c:when test="${notice.publishStatus == '숨김'}">게시</c:when>
                                <c:otherwise>숨김</c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty list}">
            <tr>
                <td colspan="7">등록된 공지사항이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

    <!-- 공지사항 테이블 아래에 페이징 영역 추가 -->
    <div class="pagination">
        <c:if test="${totalPage > 1}">
            <ul class="pagination-list">
                <!-- 이전 페이지 -->
                <c:if test="${page > 1}">
                    <li><a href="noticeList.page?page=${page - 1}">&laquo;</a></li>
                </c:if>

                <!-- 페이지 번호 목록 -->
                <c:forEach begin="${beginPage}" end="${endPage}" var="i">
                    <li class="${i == page ? 'active' : ''}">
                        <a href="noticeList.page?page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <!-- 다음 페이지 -->
                <c:if test="${page < totalPage}">
                    <li><a href="noticeList.page?page=${page + 1}">&raquo;</a></li>
                </c:if>
            </ul>
        </c:if>
    </div>

</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<script src="${contextPath}/resources/js/pages/notice.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/notice/notice.css">

<div class="main">
    <div class="notice-container">

            <div style="display: flex; justify-content: space-between; align-items: center;">
                <h2>공지사항</h2>
                <c:if test="${not empty message}">
                    <script>
                        alert("${message}");
                    </script>
                </c:if>

                <div style="display: flex; justify-content: flex-end; gap: 10px; align-items: center;">
                    <a href="${contextPath}/notice/registNotice.page" class="btn-register">등록</a>
                    <form id="deleteForm" action="${contextPath}/notice/deleteSelected.do" method="post" onsubmit="return confirm('선택한 공지사항을 삭제하시겠습니까?')" style="margin:0;">
                        <button type="submit" class="btn-delete">선택 삭제</button>
                    </form>
                </div>
            </div>

            <table class="notice-table">
                <thead>
                <tr>
                    <th><input type="checkbox" onclick="toggleAll(this)"/></th>
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
                        <td><input type="checkbox" name="noticeIds[]" value="${notice.noticeId}"/></td>
                        <td>${notice.noticeId}</td>
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
                                <c:otherwise>게시</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="${contextPath}/notice/toggleStatus.do" method="post" style="margin:0;">
                                <input type="hidden" name="noticeId" value="${notice.noticeId}" />
                                <button type="submit">
                                    <c:choose>
                                        <c:when test="${notice.publishStatus == '숨김'}">게시하기</c:when>
                                        <c:otherwise>숨겨놓기</c:otherwise>
                                    </c:choose>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty list}">
                    <tr>
                        <td colspan="8">등록된 공지사항이 없습니다.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            // 전체 선택 체크박스 함수 (기존)
            window.toggleAll = function(checkbox) {
                const checkboxes = document.querySelectorAll('input[name="noticeIds[]"]');
                checkboxes.forEach(cb => cb.checked = checkbox.checked);
            }

            // 폼 submit 이벤트 핸들러 추가
            const form = document.querySelector('form[action$="deleteSelected.do"]');
            form.addEventListener('submit', (e) => {
                const checkedBoxes = form.querySelectorAll('input[name="noticeIds[]"]:checked');
                if (checkedBoxes.length === 0) {
                    e.preventDefault(); // 폼 제출 막기
                    alert('삭제할 공지사항을 하나 이상 선택해주세요.');
                    return false;
                }
            });
        });
    </script>

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
<script src="${contextPath}/resources/js/pages/notice.js"></script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/notice/noticeDetail.css">

<div class="main">
  <h2 class="notice-title-container">
    공지사항 상세
    <span class="notice-id-badge">ID: ${notice.noticeId}</span>
  </h2>

    <table class="notice-detail-table">
      <tr><th>제목</th><td>${notice.title}</td></tr>
      <tr><th>내용</th><td><pre>${notice.description}</pre></td></tr>
      <tr><th>생성일</th><td>${notice.createdAt}</td></tr>
      <tr><th>수정일</th><td>${notice.updatedAt}</td></tr>
      <tr><th>상태</th><td>${notice.publishStatus}</td></tr>
    </table>

    <div class="notice-detail-actions">
      <div class="left-buttons">
        <a href="${contextPath}/notice/noticeList.page" class="btn btn-list">목록으로</a>

        <form action="${contextPath}/notice/toggleStatus.do" method="post" style="display:inline;">
          <input type="hidden" name="noticeId" value="${notice.noticeId}" />
          <button type="submit" class="btn btn-toggle-status">
            <c:choose>
              <c:when test="${notice.publishStatus == '숨김'}">게시하기</c:when>
              <c:otherwise>숨겨놓기</c:otherwise>
            </c:choose>
          </button>
        </form>
      </div>

      <form action="${contextPath}/notice/delete.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline;">
        <input type="hidden" name="noticeId" value="${notice.noticeId}" />
        <button type="submit" class="btn btn-delete">삭제하기</button>
      </form>
    </div>


  </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
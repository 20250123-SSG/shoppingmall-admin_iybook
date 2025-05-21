<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/notice/registNotice.css">

<div class="main">
    <h2 class="form-title">공지사항 등록</h2>
    <form action="${contextPath}/notice/regist.do" method="post" class="notice-form">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" required />
        </div>
        <div class="form-group">
            <label for="description">내용</label>
            <textarea id="description" name="description" rows="6" required></textarea>
        </div>
        <div class="form-actions">
            <button type="submit" class="btn-submit">등록</button>
            <a href="${contextPath}/notice/noticeList.page" class="btn-cancel">취소</a>
        </div>
    </form>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
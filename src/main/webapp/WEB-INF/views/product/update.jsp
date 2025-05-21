<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/product/product-regist.css"/>
<div class="main">
  <div class="content-container">
    <h2>📖 도서 수정</h2>

    <form id="registerForm" method="post" enctype="multipart/form-data"
          action="${contextPath}/product/updatebook.do">

      <input type="hidden" name="bookId" value="${book.bookId}">

      <!-- 카테고리 -->
      <div class="section-box">
        <h3 class="section-title">카테고리</h3>
        <hr>
        <div class="form-row">
          <label for="category">카테고리</label>
          <select id="category" name="categoryId">
            <option value="">선택</option>
            <c:forEach var="category" items="${categoryList}">
              <option value="${category.categoryId}"
                      <c:if test="${category.categoryId == book.categoryId}">selected</c:if>>
                ${category.categoryName}
              </option>
            </c:forEach>
          </select>
        </div>
      </div>

      <!-- 도서정보 -->
      <div class="section-box">
        <h3 class="section-title">도서정보</h3>
        <hr>
        <div class="form-row">
          <label for="title">도서명</label>
          <input type="text" id="title" name="bookName" value="${book.bookName}">
        </div>
        <div class="form-row">
          <label for="author">작가명</label>
          <input type="text" id="author" name="authorName" value="${book.authorName}">
        </div>
        <div class="form-row">
          <label for="publisher">출판사명</label>
          <input type="text" id="publisher" name="publisher" value="${book.publisher}">
        </div>
        <div class="form-row">
          <label for="publishDate">출판일</label>
          <input type="date" id="publishDate" name="publishedAt" value="${book.publishedAt}">
        </div>
      </div>

      <!-- 판매가 -->
      <div class="section-box">
        <h3 class="section-title">판매가</h3>
        <hr>
        <div class="form-row">
          <label for="price">도서금액</label>
          <input type="number" id="price" name="bookPrice" value="${book.bookPrice}">
        </div>
      </div>

      <!-- 상품이미지 -->
      <div class="section-box">
        <h3 class="section-title">상품이미지</h3>
        <hr>
        <div class="form-row">
          <label for="bookImage">도서 이미지</label>
          <div class="image-upload-box">
            <img id="imagePreview"
                 src="${book.bookImage != null ? contextPath.concat(book.bookImage) : contextPath.concat('/resources/images/no-image.png')}"
                 alt="미리보기">
            <input type="file" id="bookImage" name="image" accept="image/*" hidden>
          </div>
        </div>
      </div>

      <!-- 상세설명 -->
      <div class="section-box">
        <h3 class="section-title">상세설명</h3>
        <hr>
        <div class="form-row">
          <label for="description">책 상세설명</label>
          <textarea id="description" name="bookDescription" rows="8" placeholder="책 내용을 상세히 입력해주세요.">${book.bookDescription}</textarea>
        </div>
      </div>

      <!-- 재고수량 -->
      <div class="section-box">
        <h3 class="section-title">재고수량</h3>
        <hr>
        <div class="form-row">
          <label for="stock">재고 수량</label>
          <input type="number" id="stock" name="stock" value="${book.stock}">
        </div>
      </div>

      <!-- 상태 -->
      <div class="section-box">
        <h3 class="section-title">상태</h3>
        <hr>
        <div class="form-row">
          <label for="status">판매 상태</label>
          <select id="status" name="publishStatus">
            <option value="판매" ${book.publishStatus == '판매' ? 'selected' : ''}>판매</option>
            <option value="숨김" ${book.publishStatus == '숨김' ? 'selected' : ''}>숨김</option>
            <option value="품절" ${book.publishStatus == '품절' ? 'selected' : ''}>품절</option>
          </select>
        </div>
      </div>

      <div class="form-actions btn-area">
        <button type="submit" class="btn btn-primary">수정하기</button>
        <button type="reset" class="btn btn-secondary">초기화</button>
      </div>
    </form>
  </div>
</div>

<script>
  const contextPath = "${contextPath}";
</script>

<script src="${contextPath}/resources/js/pages/product/product-update.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>


<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="checkedStatus" value="${filter.status != null ? filter.status : ['판매','품절','숨김']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/product.css">



  <div class="main">


      <div class="container">

        <!-- 🔷 통계 영역 -->
        <div class="card stat-box">
          <div class="stat-item">전체
            <span class="stat-value" onclick="location.href='${contextPath}/product/list.page'">
              ${stats.all}
            </span>
          </div>
          <div class="stat-item">판매중
            <span class="stat-value" onclick="location.href='${contextPath}/product/list.page?status=판매'">
              ${stats.sell}
            </span>
          </div>
          <div class="stat-item">품절
            <span class="stat-value" onclick="location.href='${contextPath}/product/list.page?status=품절'">
              ${stats.sold}
            </span>
          </div>
          <div class="stat-item">판매종료
            <span class="stat-value" onclick="location.href='${contextPath}/product/list.page?status=숨김'">
              ${stats.end}
            </span>
          </div>
        </div>

        <!-- 🔶 검색 필터 영역 -->
        <div class="card search-box">
          <form method="get" action="${contextPath}/product/list.page">

            <div class="form-row">
              <label>도서번호</label>
              <input type="text" name="bookId" value="${filter.bookId}" />
              <label>도서명</label>
              <input type="text" name="bookName" value="${filter.bookName}" />
              <label>출판사명</label>
              <input type="text" name="publisher" value="${filter.publisher}" />
            </div>


            <div class="form-row">
              <label>판매상태</label>
              <input type="checkbox" id="all"
                     <c:if test="${fn:length(checkedStatus) == 3}">checked</c:if>/> 전체
              <input type="checkbox" name="status" value="판매"
                     <c:if test="${fn:contains(checkedStatus, '판매')}">checked</c:if>/> 판매중
              <input type="checkbox" name="status" value="품절"
                     <c:if test="${fn:contains(checkedStatus, '품절')}">checked</c:if>/> 품절
              <input type="checkbox" name="status" value="숨김"
                     <c:if test="${fn:contains(checkedStatus, '숨김')}">checked</c:if> /> 판매종료
            </div>

            <div class="form-row">
              <label>카테고리</label>
              <select name="categoryId">
                <option value="">대분류 선택</option>
                <c:forEach var="category" items="${categoryList}">
                <option value="${category.categoryId}"
                        <c:if test="${category.categoryId == filter.categoryId}">selected</c:if>>
                    ${category.categoryName}
                </option>
                </c:forEach>
              </select>

              <label>기간</label>
              <select name="dateType">
                <option value="created_at" <c:if test="${filter.dateType == 'created_at'}">selected</c:if>>상품등록일</option>
                <option value="updated_at" <c:if test="${filter.dateType == 'updated_at'}">selected</c:if>>최종수정일</option>
              </select>
              <div class="date-shortcuts">
                <button type="button" class="btn btn-light"  data-range="1">오늘</button>
                <button type="button" class="btn btn-light"  data-range="7">1주일</button>
                <button type="button" class="btn btn-light"  data-range="30">1개월</button>
                <button type="button" class="btn btn-light"  data-range="365">1년</button>
              </div>
              <input type="date" name="startDate" value="${filter.startDate}" />
              <input type="date" name="endDate" value="${filter.endDate}" />
            </div>

            <div class="form-actions">
              <button type="submit" class="btn btn-primary">검색</button>
              <button type="reset" class="btn btn-secondary" onclick="location.href='${contextPath}/product/list.page'">초기화</button>
            </div>

          </form>
        </div>

        <!-- 🟩 상품 목록 영역 -->
        <div class="card product-list-box">
          <div class="table-actions" style="display: flex; justify-content: space-between; align-items: center;">
            <div class="left-actions">
              <button class="btn btn-danger" id="deleteSelected">선택 삭제</button>
              <select name="status" id="statusChangeSelect">
                <option value="">판매상태 변경</option>
                <option value="sell">판매중</option>
                <option value="end">판매중지</option>
              </select>
            </div>
            <button class="btn btn-primary" id="saveChanges">수정 저장</button>
          </div>

          <table class="product-table">
            <thead>
            <tr>
              <th><input type="checkbox" /></th>
              <th>도서번호</th>
              <th>도서명</th>
              <th>출판사</th>
              <th>가격</th>
              <th>재고</th>
              <th>등록일</th>
              <th>수정일</th>
              <th>판매상태</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
              <c:when test="${empty bookList}">
                <tr>
                  <td colspan="9">
                    <div class="table-empty-message">
                      조회된 항목이 없습니다.
                    </div>
                  </td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="book" items="${bookList}">
                  <tr>
                    <td><input type="checkbox" value="${book.bookId}" /></td>
                    <td>${book.bookId}</td>
                    <td>${book.bookName}</td>
                    <td>${book.publisher}</td>
                    <td>${book.bookPrice}</td>
                    <td>${book.stock}</td>
                    <td>${book.createdAt}</td>
                    <td>${book.updatedAt}</td>
                    <td>${book.publishStatus}</td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>
        </div>

      </div>



  </div>

<script>
  const contextPath = "${contextPath}";
</script>

<script src="${contextPath}/resources/js/pages/product.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



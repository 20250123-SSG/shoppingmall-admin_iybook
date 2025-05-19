<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="checkedStatus" value="${filter.status != null ? filter.status : ['sell','sold','end']}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<link rel="stylesheet" href="${contextPath}/resources/css/pages/product.css">



  <div class="main">


      <div class="container">

        <!-- 🔷 통계 영역 -->
        <div class="card stat-box">
          <div class="stat-item">전체 <span class="stat-value">${stats.all}</span></div>
          <div class="stat-item">판매중 <span class="stat-value">${stats.sell}</span></div>
          <div class="stat-item">품절 <span class="stat-value">${stats.sold}</span></div>
          <div class="stat-item">판매종료 <span class="stat-value">${stats.end}</span></div>
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
              <input type="checkbox" name="status" value="sell"
                     <c:if test="${fn:contains(checkedStatus, 'sell')}">checked</c:if>/> 판매중
              <input type="checkbox" name="status" value="sold"
                     <c:if test="${fn:contains(checkedStatus, 'sold')}">checked</c:if>/> 품절
              <input type="checkbox" name="status" value="end"
                     <c:if test="${fn:contains(checkedStatus, 'end')}">checked</c:if> /> 판매종료
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
                <option value="createdAt" <c:if test="${filter.dateType == 'createdAt'}">selected</c:if>>상품등록일</option>
                <option value="updatedAt" <c:if test="${filter.dateType == 'updatedAt'}">selected</c:if>>최종수정일</option>
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
              <button type="reset" class="btn btn-secondary">초기화</button>
            </div>

          </form>
        </div>

        <!-- 🟩 상품 목록 영역 -->
        <div class="card product-list-box">
          <div class="table-actions">
            <button class="btn btn-danger">선택 삭제</button>
            <select name="status">
              <option value="">판매상태 변경</option>
              <option value="sell">판매중</option>
              <option value="end">판매중지</option>
            </select>
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
              <c:when test="${empty productList}">
                <tr>
                  <td colspan="9">
                    <div class="table-empty-message">
                      조회된 항목이 없습니다.
                    </div>
                  </td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="product" items="${productList}">
                  <tr>
                    <td><input type="checkbox" value="${product.bookId}" /></td>
                    <td>${product.bookId}</td>
                    <td>${product.bookName}</td>
                    <td>${product.publisher}</td>
                    <td>${product.bookPrice}</td>
                    <td>${product.stock}</td>
                    <td>${product.createdAt}</td>
                    <td>${product.updatedAt}</td>
                    <td>${product.publishStatus}</td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>
        </div>

      </div>



  </div>



<script src="${contextPath}/resources/js/pages/product.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



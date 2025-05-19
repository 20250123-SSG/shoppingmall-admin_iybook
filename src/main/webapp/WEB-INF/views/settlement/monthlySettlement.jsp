<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 조회 조건 -->
<div class="search-area">
    <form method="post" action="settleHome.jsp">
        <div class="row align-items-center">
            <div class="col-auto">
                <label for="settlementMonth" class="form-label mb-0">정산월</label>
                <input type="month" class="form-control" id="settlementMonth" name="settlementMonth"
                       value="${param.settlementMonth}" required>
                <button type="submit" class="btn btn-primary">검색</button>
            </div>
        </div>
    </form>
</div>

<!-- 정산내역 목록 -->
<div class="table-responsive">
    <table class="table table-bordered table-hover text-center align-middle">
        <thead>
        <tr>
            <th>정산기준일</th>
            <th>정산예정일</th>
            <th>정산완료일</th>
            <th>정산금액</th>
            <th>수수료합계</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty settleList}">
                <tr>
                    <td colspan="5" class="text-center py-4">데이터가 존재하지 않습니다.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="row" items="${settleList}">
                    <tr>
                        <td><fmt:formatDate value="${row.stDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${row.exDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${row.comp_date}" pattern="yyyy-MM-dd"/></td>
                        <td class="text-end"><fmt:formatNumber value="${row.stPrice}" type="number"/>원</td>
                        <td class="text-end"><fmt:formatNumber value="${row.tax}" type="number"/>원</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div> 
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

<style>
    /* sidebar가 220px 고정일 때 */
    .content-area {
        margin-left: 220px; /* sidebar 너비만큼 띄움 */
        padding: 30px 20px 20px 20px;
        min-height: 100vh;
        background: #f8f9fa;
    }

    @media (max-width: 991px) {
        .content-area {
            margin-left: 0;
        }
    }
</style>

<div class="content-area">
    <div class="container-fluid">
        <!-- 조회 조건 -->
        <div class="search-area border rounded p-3 bg-light">
            <form method="get" action="settleHome.jsp">
                <div class="form-row align-items-center">
                    <div class="col-auto">
                        <label for="startDate" class="col-form-label">정산 예정일</label>
                    </div>
                    <div class="col-auto">
                        <input type="date" class="form-control" id="startDate" name="startDate"
                               value="${param.startDate}">
                    </div>
                    <div class="col-auto">
                        ~
                    </div>
                    <div class="col-auto">
                        <input type="date" class="form-control" id="endDate" name="endDate"
                               value="${param.endDate}">
                    </div>
                    <div class="col-auto text-success small">
                        최대 1개월 이내에서 조회하실 수 있습니다.
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-success">검색</button>
                        <a href="settleHome.jsp" class="btn btn-secondary">초기화</a>
                    </div>
                </div>
            </form>
        </div>

        <!-- 정산내역 및 목록 -->
        <div class="table-area border rounded p-3 bg-white">
            <div class="d-flex justify-content-between mb-2">
                <div>
                    <b>정산내역 및 목록</b>
                </div>
                <%--                <div>--%>
                <%--                    <button class="btn btn-outline-primary btn-sm">공문서 출력</button>--%>
                <%--                    <button class="btn btn-outline-success btn-sm">엑셀다운</button>--%>
                <%--                </div>--%>
            </div>
            <table class="table table-bordered table-hover text-center">
                <thead class="thead-light">
                <tr>
                    <th>정산예정일</th>
                    <th>정산완료일</th>
                    <th>정산금액(합계)</th>
                    <th>일반정산금액</th>
                    <th>빠른정산금액</th>
                    <th>정산기준금액</th>
                    <th>수수료합계</th>
                    <th>혜택정산</th>
                    <th>일반 공제/환급</th>
                    <th>지급분류</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty settleList}">
                        <tr>
                            <td colspan="10" class="empty-data">데이터가 존재하지 않습니다.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="row" items="${settleList}">
                            <tr>
                                <td><fmt:formatDate value="${row.scheduledDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${row.completedDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatNumber value="${row.totalAmount}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.normalAmount}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.fastAmount}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.baseAmount}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.fee}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.benefit}" type="number"/></td>
                                <td><fmt:formatNumber value="${row.deduction}" type="number"/></td>
                                <td>${row.type}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
            <!-- 페이지네이션 (예시) -->
            <nav>
                <ul class="pagination">
                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/js/pages/settlement.js"></script>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>



<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:forEach var="book" items="${bookList}">
  <tr data-book-id="${book.bookId}">
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

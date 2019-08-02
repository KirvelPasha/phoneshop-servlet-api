<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentView" required="true" type="java.util.LinkedList" %>

<c:if test="${not empty recentView}">
    <br>
    <h3>Recently Viewed</h3>
    <table>
        <thead>
        <c:forEach var="product" items="${recentView}">
            <td align="center">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                <br>
                <a href="<c:url value="/products/${product.id}"/>">
                        ${product.description}
                </a>
                <br>
                <fmt:formatNumber value="${product.lastPrice}"/>
            </td>
        </c:forEach>
        </thead>
    </table>
</c:if>
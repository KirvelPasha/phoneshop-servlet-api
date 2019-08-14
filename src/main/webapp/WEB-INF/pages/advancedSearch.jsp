<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="AdvancedSearch">

    <c:if test="${not empty errors}">
        <p class="error" style="color: red"> Error</p>
    </c:if>


    <form >
        <label for="description">description</label>
        <input name="description" id="description" value="" style="text-align: right"><br>
        <label for="minPrice">Min Price</label>
        <input name="minPrice" id="minPrice" value="" style="text-align: right"><br>
        <label for="maxPrice">Max Price</label>
        <input name="maxPrice" id="maxPrice" value="" style="text-align: right"><br>
        <label for="minStock">Min Sotock</label>
        <input name="minStock" id="minStock" value="" style="text-align: right"><br>
        <label for="maxStock">maxStock</label>
        <input name="maxStock" id="maxStock" value="" style="text-align: right"><br>
        <p>
            <button>Search</button>
        </p>
    </form>


    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>
                    Description
                </td>
                <td class="price">
                    Price
                </td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td><a href="<c:url value="/products/${product.id}"/>">${product.description}</a></td>
                    <td class="price">
                        <a href="<c:url value="/products/history/${product.id}"/>">
                            <fmt:formatNumber value="${product.lastPrice}" type="currency"
                                              currencySymbol="${product.currency.symbol} "/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master>
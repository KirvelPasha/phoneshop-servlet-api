<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>

<tags:master pageTitle="Cart">

    <p>
        Cart
    </p>

    <c:if test="${not empty param.message}">
        <p style="color: aquamarine">${param.message}</p>
    </c:if>

    <c:if test="${not empty cart.cartItems}">
        <form method="post">
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Quantity</td>
                    <td class="price">Price</td>
                    <td>Actions</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                    <c:set var="product" value="${cartItem.product}"/>
                    <tr>
                        <td>
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                        </td>
                        <td>
                            <a href="products/${product.id}">${product.description}</a>
                        </td>
                        <td>
                            <input name="quantity" value="${cartItem.quantity}" style="text-align: right">
                            <c:if test="${not empty requestScope.errors[status.index]}">
                                <br><span style="color: red">${requestScope.errors[status.index]}</span>
                            </c:if>
                            <input type="hidden" name="productId" value="${cartItem.product.id}"/>
                        </td>
                        <td class="price">
                            <fmt:formatNumber value="${product.lastPrice}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                        <td>
                            <button formaction="cart/deleteCartItem/${product.id}">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" value="Update">
        </form>
    </c:if>

    <c:if test="${ empty cart.cartItems}">
        <span style="color: red">Cart is empty</span>
    </c:if>

    <tags:recentview recentView="${recentView}"></tags:recentview>

</tags:master>
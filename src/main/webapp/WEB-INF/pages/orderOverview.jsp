<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="order" class="com.es.phoneshop.order.Order" scope="request"/>

<tags:master pageTitle="Overview">
    <p>
        Overview
    </p>


    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td>Quantity</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
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
                        ${cartItem.quantity}

                </td>
                <td class="price">
                        <%--                    <fmt:formatNumber value="${product.lastPrice}" type="currency" currencySymbol="${product.currency.symbol}"/>--%>
                        ${product.lastPrice}
                </td>
            </tr>
        </c:forEach>
        <tr bgcolor="#7fffd4">
            <td><b>Total</b></td>
            <td></td>
            <td align="right"><b>${cart.totalQuantity}</b></td>
            <td align="right"><b>${cart.totalCost}</b></td>
        </tr>
        <tr bgcolor="#7fffd4">
            <td><b>Grand total</b></td>
            <td></td>
            <td align="right"><b>${order.totalCost}</b></td>
            <td></td>
        </tr>
    </table>
    <br>
    <p>First name: ${order.name}</p>
    <p>Last name: ${order.surname}</p>
    <p>Phone number: ${order.phone}</p>
    <p>Delivery mode: ${order.deliveryMode}</p>
    <p>Delivery cost: ${order.deliveryCost}</p>
    <p>Delivery date: ${order.deliveryDate}</p>
    <p>Delivery Address :${order.deliveryAddress}</p>
    <p>Payment method: ${order.paymentMethod}</p>


</tags:master>
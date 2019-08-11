<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="order" class="com.es.phoneshop.order.Order" scope="request"/>

<tags:master pageTitle="Checkout">

    <p>
        Checkout
    </p>

    <c:if test="${not empty error}">
        <p style="color: red">${error}</p>
    </c:if>

    <form method="post">
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
        <label for="name">Name</label>
        <input name="name" id="name"><br>
        <label for="surname">Surname</label>
        <input name="surname" id="surname"><br>
        <label for="phone">Phone</label>
        <input name="phone" id="phone"><br>
        <label>Payment method <select name="paymentMethod">
            <option value="cash">Cash</option>
            <option value="credit_card">Credit Card</option>
        </select></label> <br>
        <label>Delivery Mode <select name="deliveryMethod">
            <option value="courier">COURIER</option>
            <option value="storepickup">Store Pickup</option>
        </select></label><br>
        <label for="deliveryCost">Delivery Cost</label>
        <input name="deliveryCost" id="deliveryCost" value="${order.deliveryCost}" readonly="true"
               style="text-align: right"><br>
        <label for="deliveryAddress">Delivery Address</label>
        <input name="deliveryAddress" id="deliveryAddress" value="${order.deliveryAddress}"><br>
        <label for="deliveryDate">Delivery Date</label>
        <input name="deliveryDate" id="deliveryDate" value="${order.deliveryDate}" readonly="true"><br>
        <p>
            <button>Place Order</button>
        </p>
    </form>

</tags:master>
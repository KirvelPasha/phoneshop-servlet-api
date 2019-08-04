<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>

<c:if test="${not empty cart.cartItems}">
    <div>
        <a href="${pageContext.servletContext.contextPath}/cart" style="color:darkorange">
            Cart
        </a>
        Total quantity: ${cart.totalQuantity}
        Total cost: ${cart.totalCost}
    </div>
</c:if>
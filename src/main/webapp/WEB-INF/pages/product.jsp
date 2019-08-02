<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product">


    <p>
        Product details
    </p>

    <c:if test="${not empty param.message}">
        <p style="color: chartreuse">${param.message}</p>
    </c:if>

    <c:if test="${not empty param.error}">
        <p class="error" style="color: red"> Error</p>
    </c:if>

    <div>
        <img src="${product.imageUrl}">
        <p>
                ${product.description}<br>
            Price:${product.lastPrice}
        </p>
        <form method="post">
            <input name="quantity" class="price" value="${empty param.quantity ? 1 : param.quantity}">
            <c:if test="${not empty param.error}">
                <p class="error" style="color: red">${param.error}</p>
            </c:if>
            <button>Add to Cart</button>

        </form>
    </div>
    <tags:recentview recentView="${recentView}"></tags:recentview>

</tags:master>
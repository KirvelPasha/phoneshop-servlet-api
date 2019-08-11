package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.HttpSessionCartService;
import com.es.phoneshop.order.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute("order", orderService.createOrder(cart));
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String surname = request.getParameter("surname");
        String deliveryAddress = request.getParameter("deliveryAddress");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getParameter("paymentMethod").toUpperCase());
        DeliveryMode deliveryMode = DeliveryMode.valueOf(request.getParameter("deliveryMethod").toUpperCase());

        boolean hasError = Stream
                .of(name, phone, surname, deliveryAddress)
                .anyMatch(String::isEmpty);

        if (hasError || !correctPhone(phone)) {
            request.setAttribute("error", "Wrong data");
            doGet(request, response);
            return;
        }

        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.createOrder(cart);
        order.setName(name);
        order.setPhone(phone);
        order.setSurname(surname);
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryMode(deliveryMode);

        orderService.placeOrder(order);
        cartService.clear(cart);

        String secureId = order.getSecureId();
        response.sendRedirect(request.getContextPath() + "/order/overview/" + secureId);
    }

    private boolean correctPhone(String phone) {
        return phone.matches("(\\+)\\d{12}");
    }

}

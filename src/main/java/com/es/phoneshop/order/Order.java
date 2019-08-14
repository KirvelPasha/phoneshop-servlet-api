package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Order extends Cart {
    private Long id;
    private String secureId;
    private String name;
    private String surname;
    private String phone;
    private DeliveryMode deliveryMode;
    private Date deliveryDate;
    private BigDecimal deliveryCost;
    private String deliveryAddress;
    private BigDecimal subTotalPrice;
    private BigDecimal totalCost;
    private PaymentMethod paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }

    @Override
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    @Override
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(secureId, order.secureId) &&
                Objects.equals(name, order.name) &&
                Objects.equals(phone, order.phone) &&
                deliveryMode == order.deliveryMode &&
                Objects.equals(deliveryDate, order.deliveryDate) &&
                Objects.equals(deliveryCost, order.deliveryCost) &&
                Objects.equals(subTotalPrice, order.subTotalPrice) &&
                Objects.equals(totalCost, order.totalCost) &&
                paymentMethod == order.paymentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, secureId, name, phone, deliveryMode, deliveryDate, deliveryCost, subTotalPrice, totalCost, paymentMethod);
    }
}

package com.brewandbite.model.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.brewandbite.model.items.MenuItem;

public class Order {

    private int orderId;
    private String customerName;
    private List<MenuItem> items;
    private double totalPrice;
    private LocalDateTime orderTime;
    private OrderStatus status;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public String getCustomerName() {
        return customerName;
    }

    // Constructor, getters, setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotal() {
        this.totalPrice = 0.0;
        for (MenuItem item : items) {
            this.totalPrice += item.calculatePrice();
        }
    }

    public void addItem(MenuItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.items.add(item);
        calculateTotal();
    }

    public enum OrderStatus {
        PENDING, // Just placed, waiting for barista
        IN_PROGRESS, // Barista is working on it
        READY, // Ready for customer pickup
        COMPLETED       // Customer has picked up
    }

    @Override
    public String toString() {
        return "Order{"
                + "customerName='" + customerName + '\''
                + ", orderId=" + orderId
                + ", items=" + items
                + ", totalPrice=" + totalPrice
                + ", orderTime=" + orderTime
                + ", status=" + status
                + '}';
    }
}

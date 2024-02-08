package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrdersByUserEmail(String email);
}

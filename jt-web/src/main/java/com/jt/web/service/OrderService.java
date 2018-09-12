package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface OrderService {

	List<Cart> findOrderByUserId(Long userId);
}

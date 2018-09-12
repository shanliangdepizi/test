package com.jt.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.service.OrderService;
import com.jt.wen.thread.UserThreadLocal;
@Controller
@RequestMapping("/order")
public class OrderController {
	private OrderService orderService;
	@RequestMapping("/create")
	public String findOrderByUserId(Model model){
		Long userId = UserThreadLocal.get().getId();
		List<Cart> orderList = orderService.findOrderByUserId(userId);
		return "order-cart";
	}
}

package com.jt.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.service.BaseService;
@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService {
	@Autowired
    private CartMapper cartMapper;

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}

	/**
	 * 根据userId/itemId查询购物车信息
	 *   如果购物车不存在
	 *      新建购物车，添加到数据
	 * 
	 */
	@Override
	public void saveCart(Cart cart) {
		//通过userid和itemId查询
		Cart cartDB = cartMapper.findCartByUI(cart);
		if(cartDB == null){
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num = cart.getNum() + cartDB.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(new Date());
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}
		
	}

	@Override
	public void updateCartNum(Cart cart) {
		cart.setUpdated(new Date());
		cartMapper.updateCartnum(cart);
	}

	@Override
	public void deleteCart(Cart cart) {
		cartMapper.delete(cart);
	}
}

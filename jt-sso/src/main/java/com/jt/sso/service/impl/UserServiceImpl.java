package com.jt.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Autowired
	private UserMapper userMapper;
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 实现思路： 1.param : 用户输入的内容 2.type: 1 2 3判断不同
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		// 根据类型定义查询的字段
		String column = null;
		switch (type) {
		case 1:
			column = "username";
			break;
		case 2:
			column = "phone";
			break;
		case 3:
			column = "email";
			break;
		}
		
		int count = userMapper.findCheckUser(column, param);
		return count == 0 ? false : true;
	}

	@Override
	public void saveUser(User user) {
		//补齐参数 盐值都是公司域名+用户名
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		User userDB = super.queryByWhere(user);
		if(userDB == null){
			//表示用户名密码  错误
			throw new RuntimeException();
		}
		String token = DigestUtils.md5Hex("jt_TICKET"+System.currentTimeMillis()+userDB.getUsername());
		try {
			String userJSON = objectMapper.writeValueAsString(userDB);
			jedisCluster.setex(token, 3600*24*7, userJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return token;
	}
}

package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.wen.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

//Handler 处理器 作用  真正执行controller、service...
public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	private static ObjectMapper objectMapper = new ObjectMapper();
	//在执行controller方法之前执行
	@Override
	/**
	 * beelean 是否放行
	 *    true: 放行，用户可以跳转页面
	 *    false:拦截，重定向一个页面
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 1.判断用户客户端是否有cookie（token），
		如果用户没有token，则重定向到登录页面
		如果用户token中有数据，则取redis中获取缓存数据
		如果redis中没有数据，则跳转到登录页面
		如果redis中有数据，则放行请求
		 */
		String token = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.toString());
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		//判断是否为空串
		if(!StringUtils.isEmpty(token)){
			
			//检查缓存中是否有数据
			String userJSON = jedisCluster.get(token);
			
			if(!StringUtils.isEmpty(userJSON)){
				//用户已经登录
				User user = objectMapper.readValue(userJSON, User.class);
				UserThreadLocal.set(user);
				return true;
			}
		}
		//表示用户没有登录
		response.sendRedirect("/user/login.html");
		return false;
	}

	//刚执行完业务逻辑之后拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	//请求全部执行完成，返回页面之前  拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
	}
	
}

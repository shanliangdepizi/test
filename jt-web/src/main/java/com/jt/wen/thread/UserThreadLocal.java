package com.jt.wen.thread;

import com.jt.web.pojo.User;

/**
 * 
 * @author 000 如果参数需要传递多值，则用MAP封装
 */
public class UserThreadLocal {
	private static ThreadLocal<User> userThread = new ThreadLocal<>();

	public static void set(User user) {
		userThread.set(user);
	}

	public static User get() {
		return userThread.get();
	}

	public static void remove() {
		userThread.remove();
	}
}

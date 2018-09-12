package com.jt.redis;

import java.util.Calendar;

import org.springframework.beans.factory.FactoryBean;

public class SpringFactory implements FactoryBean<Calendar>{

	@Override
	public Calendar getObject() throws Exception {
		System.out.println("我是springFactory");
		return Calendar.getInstance();
	}

	@Override
	public Class<?> getObjectType() {
		return Calendar.class;
	}

	@Override
	public boolean isSingleton() {
		// 是否是单例模式
		return true;
	}

}

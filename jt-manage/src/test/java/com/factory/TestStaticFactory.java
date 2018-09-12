package com.factory;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestStaticFactory {

	@Test
	public void testStaticFatory(){
		
	}
	@Test
	public void testSpringFactory(){
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/spring/factory.xml");
		Calendar calendar = (Calendar) context.getBean("calendar3");
		System.out.println(calendar.getTime());
	}
}

package com.jt.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestMap {

	@Test
	public void testMap(){
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "我");
		map.put(2, "龚英杰");
		map.put(3, "来露个脸！");
		map.put(4, "我");
		map.put(5, "龚英杰");
		map.put(6, "来露个脸！");
		map.put(7, "我");
		map.put(8, "龚英杰");
		map.put(9, "来露个脸！");
		map.put(10, "我");
		map.put(11, "龚英杰");
		map.put(12, "来露个脸！");
		map.put(13, "我");
		map.put(14, "龚英杰");
		map.put(15, "来露个脸！");
		System.out.println(map.toString());
	}
}

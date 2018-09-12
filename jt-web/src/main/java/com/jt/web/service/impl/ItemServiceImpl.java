package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	//前台调用后台的方法获取item数据
	@Override
	public Item findItemById(Long itemId) {
		//1.定义远程的url
		String url = "http://manage.jt.com/web/item/findItemById";
//		String charset = "utf-8";
		//添加参数
		Map<String, String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		//放回响应结果
		String result = httpClientService.get(url, params);
		Item item = null;
		
		try {
			//将json转成java对象
			item = objectMapper.readValue(result, Item.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}
	/**
	 * 
	 */
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		Map<String, String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		String itemDescJson = httpClientService.get(url, params);
		ItemDesc itemDesc = null;
		try {
			itemDesc = objectMapper.readValue(itemDescJson, ItemDesc.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}

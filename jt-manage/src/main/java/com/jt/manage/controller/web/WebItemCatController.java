package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/web")
public class WebItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	//实现跨域的商品分类信息的展现
	
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public MappingJacksonValue findItemCatAll(String callback){
//		itemCatService.findItemCatAllCache();
		ItemCatResult itemCatResult = itemCatService.findItemCatAll();
		MappingJacksonValue jacksonValue = 
				new MappingJacksonValue(itemCatResult);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}

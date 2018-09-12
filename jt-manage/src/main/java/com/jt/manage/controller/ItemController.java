package com.jt.manage.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	private static final Logger logger = Logger.getLogger(ItemController.class);
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/query")
	@ResponseBody // 将对象转换成JSON串
	public EasyUIResult finditemByPage(Integer page, Integer rows){
		EasyUIResult item = itemService.findItemByPage(page,rows);
		return item;
	}
	/**
	 * responseBody
	 *    如果解析是对象  则默认使用的是UTF-8编码
	 *    如果解析的是String字符串，则默认使用ISO-8859-1
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/cat/queryItemName", produces="html/text;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId){
		return itemService.findItemCatNameById(itemId);
	}
	
	/**
	 * 实现商品新增
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc){
		try {
			itemService.saveItem(item, desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "添加商品失败！"); 
	} 
	
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			logger.info("！！！！！！！！！更新成功！");
			return SysResult.oK();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return SysResult.build(201, "更新失败");
	}
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids){
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除商品失败");
	}
	
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instockItems(Long [] ids){
		try {
			int status = 2;
			itemService.updateState(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "商品下架失败");
	}
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelfItems(Long [] ids){
		try {
			int status = 1;
			itemService.updateState(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SysResult.build(201, "商品下架失败");
	}
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable Long itemId){
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败");
	}
}

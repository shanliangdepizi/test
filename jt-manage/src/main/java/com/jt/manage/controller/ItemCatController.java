package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
   @Autowired
   private ItemCatService itemCatService;
   /**
    * 实现是商品分类目录显示
    * 为了实现EasyUI的树形结构。如果参数没有传值
    * @RequestParam(defaultValue="",value="")
    * defaultValue  如果参数没有传值，则使用默认的值
    * required
    * value 
    * @param parentId
    * @return
    */
   @RequestMapping("/list")
   @ResponseBody
   public List<EasyUITree> findItemCat(@RequestParam(defaultValue="0",value="id") Long parentId){
	   List<EasyUITree> treeList = itemCatService.findItemCatCache(parentId);
	   return treeList;
   }
}

package com.jt.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<EasyUITree> findItemCat(Long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			String state = itemCatTemp.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	@Override
	public List<EasyUITree> findItemCatCache(Long parentId) {
		List<EasyUITree> treeList = null;
		String key = "ITEM_CAT_" + parentId;
		String jsonData = jedisCluster.get(key);
		try {
			if (StringUtils.isEmpty(jsonData)) {
				// 缓存中没有数据
				treeList = findItemCat(parentId);
				String json = objectMapper.writeValueAsString(treeList);
				jedisCluster.set(key, json);
				return treeList;
			} else {
				// 缓存中有数据 [{对象},{},{},{}]
				EasyUITree[] trees = objectMapper.readValue(jsonData, EasyUITree[].class);
				treeList = Arrays.asList(trees);
				System.out.println("查询缓存");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeList;
	}

	/**
	 * 
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		//查询数据
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1);
		List<ItemCat> itemCatAllList = itemCatMapper.select(itemCatTemp);
		Map<Long,List<ItemCat>> map = new HashMap<>();
		
		for(ItemCat itemCat : itemCatAllList){
			
			if(map.containsKey(itemCat.getParentId())){
				map.get(itemCat.getParentId()).add(itemCat);
			}else{
				List<ItemCat> childItemCatData = new ArrayList<>();
				childItemCatData.add(itemCat);
				map.put(itemCat.getParentId(), childItemCatData);
			}
		}
		//回显数据
		
		ItemCatResult itemCatResult = new ItemCatResult();
		
		//一级分类信息
		List<ItemCatData> itemCatDataList1 = new ArrayList<>();
		
		for(ItemCat itemCat1 : map.get(0L)){
			
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href = '"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			itemCatDataList1.add(itemCatData1);
			
			//二级分类信息
			
			List<ItemCatData> itemCatDataList2 = new ArrayList<>();
			
			for(ItemCat itemCat2 : map.get(itemCat1.getId())){
				
				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("/products/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				itemCatDataList2.add(itemCatData2);
				
				//三级分类信息
				
				List<String> itemCatData3 = new ArrayList<>();
				
				for(ItemCat itemCat3 : map.get(itemCat2.getId())){
					itemCatData3.add("/products/"+itemCat3.getId()+" | "+itemCat3.getName());
				}
				itemCatData2.setItems(itemCatData3);
			}
			itemCatData1.setItems(itemCatDataList2);
			//控制一级菜单显示长度
			if(itemCatDataList1.size() > 13){
				break;
			}
		}
		
		itemCatResult.setItemCats(itemCatDataList1);
		return itemCatResult;
	}

	@Override
	public ItemCatResult findItemCatAllCache() {
		ItemCatResult itemCatResult = null;
		String key = "ITEM_CAT_ALL";
		String result = jedisCluster.get(key);
		try {
			
			if(StringUtils.isEmpty(result)){
				itemCatResult = findItemCatAll();
				String json = objectMapper.writeValueAsString(itemCatResult);
				jedisCluster.set(key, json);
				System.out.println("第一次执行，查询数据库");
			}else{
				itemCatResult = objectMapper.readValue(result, ItemCatResult.class);
				System.out.println("通过缓存查询数据库");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatResult;
	}

}

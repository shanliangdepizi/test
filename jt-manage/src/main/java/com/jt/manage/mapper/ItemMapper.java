package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{

	int findItemCount();

	/**
	 * mybatis 不允许多值传参
	 *    思路： 将多值转换成单值
	 *          使用对象封装数据    新增、修改
	 *          使用map进行封装数据 
	 *                多条件查询时候  参数不是POJO的属性
	 *          使用array、list封装数据
	 * @param start
	 * @return
	 */
	List<Item> findItemListByPage(@Param("start") Integer start, @Param("rows") Integer rows);

	@Select("select name from tb_item_cat where id = #{id}")
	String findItemCatNameById(Long itemId);

	void updateStatue(@Param("ids")Long[] ids, @Param("status")int status);
}

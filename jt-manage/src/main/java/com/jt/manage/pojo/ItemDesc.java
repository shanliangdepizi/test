package com.jt.manage.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;
@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{

	private static final long serialVersionUID = 1L;
	@Id
	private Long itemId;//与商品表保持一致
	private String itemDesc;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
}

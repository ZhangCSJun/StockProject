package cn.zj.cloud.admin.model;

import cn.zj.cloud.constant.Constant;

public class DeleteStockExchangeRequest {
	private String id = Constant.EMPTY_STRING;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

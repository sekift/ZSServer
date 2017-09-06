package com.huishu.ait.entity.dto;

import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.entity.common.SearchModel;

/**
 * 账号查询的DTO
 * @author yindq
 * @date 2017年8月3日
 */
public class AccountSearchDTO extends SearchModel {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/** 会员类型 */
	private String type;
	/** 日期 */
	private String day;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}

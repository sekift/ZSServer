package com.huishu.ZSServer.controller.intelligent;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.huishu.ZSServer.common.AjaxResult;
import com.huishu.ZSServer.common.conf.MsgConstant;
import com.huishu.ZSServer.controller.BaseController;
import com.huishu.ZSServer.entity.IndusCompany;
import com.huishu.ZSServer.service.company.EnterPriseService;
import com.huishu.ZSServer.service.company.IndusCompanyService;

/**
 * @author hhy
 * @date 2017年11月30日
 * @Parem
 * @return 
 * 智能推荐相关接口文档
 */
@Controller
@RequestMapping("/intelligent")
public class IntelligentPushController extends BaseController{
	private Logger LOGGER = LoggerFactory.getLogger(IntelligentPushController.class);
	@Autowired
	private  IndusCompanyService  service;
	
	@Autowired
	private EnterPriseService epservice;
	/**
	 * 直接跳转页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{page}", method = RequestMethod.GET)
	public String show(@PathVariable String page) {
		return "/search/"+page;
	}
	
	/**
	 * 获取智推企业列表数据
	 */
	@ResponseBody
	@RequestMapping(value="/list.json",method=RequestMethod.GET)
	public AjaxResult getInteList(){
		Iterable<IndusCompany> list = service.listCompany();
		
		return success(list);
	}
	
	/**
	 * 根据别名查看详细信息
	 */
	@ResponseBody
	@RequestMapping(value="/getCompanyInfoByName.json",method=RequestMethod.POST)
	public AjaxResult getCompanyInfoByName(@Param("name")String companyName){
		
		if(StringUtil.isEmpty(companyName)){
			return error(MsgConstant.ILLEGAL_PARAM);
		}
		IndusCompany company = service.findCompanyInfoByCompanyName(companyName);
		if(company==null){
			return error(MsgConstant.SYSTEM_ERROR);
		}
		//根据全称获取相应的信息
		JSONObject obj = epservice.getCompanyInfoByCompany(company.getCompany());
		if(obj==null){
			return error(MsgConstant.SYSTEM_ERROR);
		}
		return success(obj);
	}
	
}
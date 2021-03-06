package com.huishu.ManageServer.service.enterprise.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.huishu.ManageServer.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.huishu.ManageServer.entity.dbFirst.Enterprise;
import com.huishu.ManageServer.entity.dbFirst.IndusCompany;
import com.huishu.ManageServer.repository.first.IndusCompanyRepository;
import com.huishu.ManageServer.service.enterprise.IndustryCompanyService;

/**
 * @author hhy
 * @date 2018年2月5日
 * @Parem
 * @return 
 * 
 */
@Service
public class IndustryCompanyServiceImpl implements IndustryCompanyService {
	
	@Autowired
	private IndusCompanyRepository rep;

	//删除原本的数据
	@Override
	public boolean deleteAll(Long userId) {
				List<IndusCompany> list = rep.findByUserId(userId);
				try {
					rep.delete(list);
					return true;
				} catch (Exception e) {
					
					return false;
				}
	}
	
	@Override
	public boolean saveListCompany(List<Enterprise> list,Long userId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		List<IndusCompany> ll = new ArrayList<IndusCompany>();
		list.forEach(action->{
			IndusCompany ent = new IndusCompany();
			ent.setId(action.getId());
			ent.setCompany(action.getCompany());
			ent.setCompanyName(action.getCompanyName());
			ent.setIndustry(action.getIndustry());
			ent.setIndustryLabel(action.getIndustryLabel());
			ent.setInduszero(action.getIndustryZero());
			if( StringUtil.isEmpty(action.getCreateTime())){
				ent.setCreateTime(df.format(new Date()));
			}else{
				ent.setCreateTime(action.getCreateTime());
			}
			if(StringUtil.isEmpty(action.getUpdateTime())){
				ent.setUpdateTime(df.format(new Date()));
			}else{
				ent.setUpdateTime(action.getUpdateTime());
			}

			ent.setUserId(userId);
			ll.add(ent);
		});
		try {
			rep.save(ll);
			return true;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
	}

	@Override
	public List<IndusCompany> ListAllInfo() {
		try {
			return (List<IndusCompany>) rep.findAll();
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	public boolean deleteInfoById(String id) {
		try {
			Long _id = Long.parseLong(id);
			rep.delete(_id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	@Override
	public boolean saveOrUpdateInfo(IndusCompany ent) {
		try {
			 rep.save(ent);
			return true;
		} catch (Exception e) {

			return false;
		}
	}
}

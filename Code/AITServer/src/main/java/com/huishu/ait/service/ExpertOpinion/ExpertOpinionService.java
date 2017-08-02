package com.huishu.ait.service.ExpertOpinion;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.es.entity.ExpertOpinionDTO;

/**
 * @author yxq
 *
 */
public interface ExpertOpinionService {
	//根基热度和发布时间对查询查询结果进行排序
	JSONArray getExertOpinionList(ExpertOpinionDTO expertOpinionDTO);
	//根据作者查询专家观点
	JSONArray findExpertOpinionByAuthor(ExpertOpinionDTO requestParam);
	//根据id查询专家观点详情
	JSONObject findExpertOpinionById(String id);
	//收藏专家观点文章
	public Boolean expertOpinionCollect(String id);
	//取消收藏专家观点文章
	public Boolean cancelExpertOpinionCollect(String id);
}

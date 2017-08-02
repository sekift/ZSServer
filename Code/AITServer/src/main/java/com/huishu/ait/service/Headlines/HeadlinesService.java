package com.huishu.ait.service.Headlines;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.echart.Option;
import com.huishu.ait.es.entity.AITInfo;
import com.huishu.ait.es.entity.dto.HeadlinesDTO;
import com.huishu.ait.es.entity.dto.HeadlinesArticleListDTO;

/**
 * @author hhy
 * @date 2017年7月27日
 * @Parem
 * @return 
 * 
 */
public interface HeadlinesService {
	/** 产业头条--关键词云 */
	JSONArray  getWordCloud(HeadlinesDTO headlinesDTO); 
	
	/** 产业头条--媒体云图 */
	Option  getCarClondChartList(HeadlinesDTO headlinesDTO);

	/**
	 * 产业头条--今日头条
	 * @param id
	 * @return
	 */
	Page<HeadlinesArticleListDTO> findArticleByVector(HeadlinesDTO headlinesDTO);

	/**
	 * 根据id查询文章信息
	 * @param id
	 * @return
	 */
	AITInfo findArticleById(String id);
}

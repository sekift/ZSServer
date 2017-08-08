package com.huishu.ait.service.companyIntelligence.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.common.util.DateCheck;
import com.huishu.ait.common.util.ESUtils;
import com.huishu.ait.es.entity.dto.CompanyIntelligenceDTO;
import com.huishu.ait.service.companyIntelligence.CompanyIntelligenceService;

/**
 * @author yxq
 * @date 2017年8月4日
 * @功能描述：企业情报画像的实现类
 */
@Service
public class CompanyIntelligenceServiceImpl implements CompanyIntelligenceService {
	
	private static Logger log = LoggerFactory.getLogger(CompanyIntelligenceServiceImpl.class);
	
	@Autowired
	private Client client;
	/**
	 * @return
	 * 获取企业情报列表信息
	 */
	public JSONArray getCompanyIntelligenceList(CompanyIntelligenceDTO dto){
		JSONArray result = new JSONArray();
		try {
			//获取参数
			String company = dto.getBusiness();
			String emotion = dto.getEmotion();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			
			String startDate = format2.format(format1.parse(dto.getStartDate()));
			String endDate = format2.format(format1.parse(dto.getEndDate()));
			String dateCheck = DateCheck.dateCheck(dto.getTimeFlag());
			if (null == startDate && null == endDate) {
				if (null != dateCheck) {
					String[] split = dateCheck.split("@");
					startDate = split[0];
					endDate = split[1];			
				}
			}
			Integer pageNumber = dto.getPageNumber();
			Integer pageSize = dto.getPageSize();
			int from = (pageNumber - 1)*pageSize;
			
			SearchRequestBuilder requestBuilder = ESUtils.getSearchRequestBuilder(client);
			BoolQueryBuilder bq = QueryBuilders.boolQuery();
			if (StringUtils.isNotBlank(company)) {
				bq.must(QueryBuilders.termQuery("business", company));
			}
			if (StringUtils.isNotBlank(emotion)) {
				if ("正面".equals(emotion)) {
					bq.must(QueryBuilders.termQuery("emotion", "positive"));
				}
				if ("负面".equals(emotion)) {
					bq.must(QueryBuilders.termQuery("emotion", "negative"));
				}
			}
			if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
				bq.must(QueryBuilders.rangeQuery("publishDate").from(startDate).to(endDate));
			}
			SearchResponse response = requestBuilder.setQuery(bq)
					.addSort("publishDateTime", SortOrder.DESC)
					.setFrom(from).setSize(pageSize)
					.execute().actionGet();
			SearchHits hits = response.getHits();
			for (SearchHit searchHit : hits) {
				Map<String, Object> map = searchHit.getSource();
				if (null != map && map.size()> 0 ){
					map.put("_id", searchHit.getId());
					map.put("total", hits.getTotalHits());
					JSONObject json = new JSONObject();
					json.put("id", map.get("_id"));
					json.put("publishDate", map.get("publishDate"));
					json.put("title", map.get("title"));
					json.put("content", map.get("content"));
					json.put("source", map.get("source"));
					json.put("dimension", map.get("dimension"));
					result.add(json);
				}
			}
			return result;
		} catch (Exception e) {
			log.error("获取企业情报失败", e.getMessage());
			return null;
		}
		
	}
}

package com.huishu.ManageServer.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.huishu.ManageServer.es.entity.Essay1;

/**
 * 查看文章详情
 * 
 * @author yindq
 * @date 2017年12月6日
 */
@Repository
public interface EssayElasticsearch extends ElasticsearchRepository<Essay1, String> {
}



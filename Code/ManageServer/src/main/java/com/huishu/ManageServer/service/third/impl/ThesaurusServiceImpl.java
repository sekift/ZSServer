package com.huishu.ManageServer.service.third.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ManageServer.entity.dbThird.ThesaurusEntity;
import com.huishu.ManageServer.entity.dto.dbThird.AttributeDTO;
import com.huishu.ManageServer.entity.dto.dbThird.AttributeInfo;
import com.huishu.ManageServer.entity.dto.dbThird.Serizal;
import com.huishu.ManageServer.entity.dto.dbThird.TKeyWordDTO;
import com.huishu.ManageServer.entity.dto.dbThird.WordDataDTO;
import com.huishu.ManageServer.entity.dto.dbThird.addKeyWordDTO;
import com.huishu.ManageServer.repository.third.AttributeRepository;
import com.huishu.ManageServer.repository.third.KeyWordInfoRepository;
import com.huishu.ManageServer.repository.third.KeyWordRelatedRepository;
import com.huishu.ManageServer.repository.third.KeyWordRelatetionRepository;
import com.huishu.ManageServer.repository.third.KeywordTypeRepository;
import com.huishu.ManageServer.repository.third.LogRepository;
import com.huishu.ManageServer.repository.third.ThesaurusRepository;
import com.huishu.ManageServer.service.third.ThesaurusService;
import com.huishu.ManageServer.common.util.StringUtil;
import com.huishu.ManageServer.config.TargetDataSource;
import com.huishu.ManageServer.entity.dbThird.AttributeEntity;
import com.huishu.ManageServer.entity.dbThird.KeyWordRelatedEntity;
import com.huishu.ManageServer.entity.dbThird.KeywordInfoEntity;
import com.huishu.ManageServer.entity.dbThird.KeywordTypeEntity;
import com.huishu.ManageServer.entity.dbThird.Log;
import com.huishu.ManageServer.entity.dbThird.RelatedWordEntity;
/**
 * @author hhy
 * @date 2018年3月16日
 * @Parem
 * @return 
 * 
 */
@Service
@Transactional
public class ThesaurusServiceImpl implements ThesaurusService {
	private static final Logger LOGGER = Logger.getLogger(ThesaurusServiceImpl.class);
	
	@Resource  
	private  ThesaurusRepository rep;
	
	@Resource
	private KeyWordRelatedRepository krp;

	@Resource
	private LogRepository lrp;
	
	@Resource
	private AttributeRepository arp;
	
	@Resource
	private KeywordTypeRepository ktp;

	@Resource
	private KeyWordInfoRepository kip;
	
	@Resource
	private KeyWordRelatetionRepository kwp;
	
	
	@TargetDataSource(name="third")
	@Override
	public JSONArray findAllKeyWord() {
		JSONArray obj = new JSONArray();
		
		return obj;
	}

	/**
	 * 查看关键词信息--列表查看
	 */
	@Override
	@TargetDataSource(name="third")
	public Page<WordDataDTO> findByPage(TKeyWordDTO dto) {
		Page<WordDataDTO> page = null;
		List<ThesaurusEntity> list  = null;
		List<WordDataDTO> alist = new ArrayList<WordDataDTO>();
		Long count = null;
		try {
			Long number = (long) (dto.getPageNumber()*(dto.getPageSize()));
			Pageable pageable = new PageRequest(dto.getPageNumber() ,dto.getPageSize());
			if(dto.getType().equals("全部")){
				switch(dto.getSort()){
					case("1"):
						//按照添加时间倒序
						list = rep.getKeyWordListDESC(number,dto.getPageSize());
						//获取相应的总数，
						count = rep.count();
					  break;
					case("2"):
						//按照添加时间顺序
						list = rep.getKeyInfoList(number,dto.getPageSize());
						//获取相应的总数，
//						count = rep.countByType(dto.getType());
					  break;
					case("3"):
						//按照词性热度
						//需要配合调试
					  break;
					case("4"):
						//按照词性复杂度
						//先获取所有属性复杂度高的关键词id
						List<Long> ids = arp.getKeyWordId(number,dto.getPageSize());
						//根据关键词id集合查询所有数据
						/*list = rep.findAll(ids);
						count = arp.getKeyWordIdCount();*/
					   break;
					default :
						//词性关系复杂度
						List<Long> all = krp.getKeyWordIdDESC(number, dto.getPageSize());
						//根据关键词id集合查询所有数据
//						list = rep.findAll(all);
						count = arp.getKeyWordIdCount();
						break;
				};
				
			}else{
				Long typeId = 	Long.parseLong(dto.getType());
				switch(dto.getSort()){
				case("1"):
					//按照添加时间倒序
					//list = rep.getKeyWordListDESC(number,dto.getPageSize());
					List<Long> _ids = kip.getKeyWordListDESCByType(typeId , number, dto.getPageSize());
//					list = rep.findAll(_ids);
					//获取相应的总数，
					 count = kip.getCount(typeId);
					 break;
				case("2"):
					//按照添加时间顺序
					List<Long> ids = kip.getKeyWordListByType(typeId , number, dto.getPageSize());
//					list = rep.findAll(ids);
					//获取相应的总数，
					count = kip.getCount(typeId);
				  break;
				case("3"):
					//按照词性热度
					//需要配合调试
				  break;
				case("4"):
					//按照词性复杂度
					
					//先获取所有的id
					List<Integer> ll = kip.getWordIdByTypeId(typeId);
					
					//先获取所有属性复杂度高的关键词id
					
					//根据关键词id集合查询所有数据
					
				   break;
				default :
					//词性关系复杂度
				
					//根据关键词id集合查询所有数据
				
				  break;
			}
				
			}
			list.forEach(action->{
				WordDataDTO dtp = new WordDataDTO();
				dtp.setEntntity(action);
				KeywordInfoEntity ent = kip.findByWordId(action.getId());
				KeywordTypeEntity one = ktp.findOne(ent.getTypeId());
				dtp.setKeywordNumber(ent.getWordNumber());
				dtp.setTypeWord(one.getTypeWord());
				alist.add(dtp);
			});
			 page = new PageImpl<>(alist, pageable, count);
			 return page;
		} catch (Exception e) {
			LOGGER.debug("列表查看词库关键词失败！原因是：",e);
			return null;
		}
	}

	/**
	 * 获取关联关系
	 */
	@Override
	@TargetDataSource(name="third")
	public JSONObject findRelatedById(String id) {
		JSONObject obj = new JSONObject();
		try{
			
		} catch (Exception e) {
			LOGGER.debug("根据id查看关联关系失败！原因是：",e);
			obj.put("result", "false");
		}
		return obj;
	}

	/**
	 * 删除关联关系
	 */
	@Override
	@TargetDataSource(name="third")
	public boolean deleteRelatedById(String id) {
		try {
		} catch (Exception e) {
			LOGGER.debug("根据id删除关联关系失败！原因是：",e);
			return false;
		}
		return true;
	}

	
	@Override
	@TargetDataSource(name="third")
	public boolean saveOrUpdateInfo(addKeyWordDTO dto) {
		ThesaurusEntity ent = new ThesaurusEntity();
		try {
			  return true;
		} catch (Exception e) {
			LOGGER.error("新增或保存词失败,原因是：",e);
			return false;
		}
		
	}

	
	@Override
	@TargetDataSource(name="third")
	public boolean deleteRelatedInfoById(String id) {
		try {
			long _id = Long.parseLong(id);
			krp.delete(_id);
			return true;
		} catch (Exception e) {
			LOGGER.error("根据id删除关联关系失败,原因是：",e);
			return false;
		}
	}

	@Override
	@TargetDataSource(name="third")
	public boolean printLog(String originalFilename, String message) {
		Log log = new Log();
		Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdate = sdf.format(date);
		log.setCreateTime(createdate);
		log.setMessage(message);
		log.setName(originalFilename);
		Log save = lrp.save(log);
		if (save == null) {
			return false;
		}
		return true;
	}

	
	@Override
	@TargetDataSource(name="third")
	public boolean addDataInfo(String value) {
		ThesaurusEntity ent = null;
		ThesaurusEntity save =  null;
		Long wordId = null;
		Long count = (long) 0;
		try {
			String[] split = value.split("---");
			String keyword = split[0];
			String type = split[1];
			if(split.length<=2){
//				 rep.countByType(type);
				Long typeId = null;
				if(count==0){
					Long keyWordId = rep.getKeyWordId();
					typeId = keyWordId+1;
				}else{
//					typeId = rep.getTypeIdByType(type);
				}
				ent =new ThesaurusEntity();
				ent.setKeyword(keyword);
				rep.save(ent);
			}else{
				String describe = split[2];
//				Long count = rep.countByType(type);
				ent = new ThesaurusEntity();
				ent.setDescribe(describe);
				ent.setKeyword(keyword);
				//如果count==0,说明此类型词没有出现，进行添加
				if( count == 0){
					//获取产业的最大值id
					Long _id = rep.getKeyWordId();
					Long id = _id+1;
					ent.setId(id);
				}else{
					//获取typeid
//					Long typeId = rep.getTypeIdByType(type);
					Long mid = rep.getMaxId();
					//如果mid处于0~20之间，说明产业数据太小，需要扩大
					if(mid>0&&mid<20){
						ent.setId((long) 20);
					}else{
						ent.setId(mid+1);
					}
				}
				//判断关键词是否存在
				save = rep.findByKeyword(keyword);
				//如果通过关键词查找为空，则进行新增
				if(save == null){
					save = rep.save(ent);
					wordId = save.getId();
				}else{
					//如果不为空，则直接获取数据
					wordId = save.getId();
				}
				List<AttributeEntity> list = new ArrayList<AttributeEntity>();
				for(int i=3;i<split.length;i++){
					String info = split[i];
					int i1 = info.indexOf("(");
					int i2 = info.indexOf(")");
					String name = info.substring(0, i1);
					String val = info.substring(i1+1, i2);
					AttributeEntity ant = new AttributeEntity();
//					ant.setEntity(save);
					ant.setWordId(wordId);
					ant.setAttributeName(name);
					ant.setAttributeValue(val);
					list.add(ant);
				}
				arp.save(list);
			}
			
			return true;
		} catch (Exception e) {
			LOGGER.error("添加一个实体数据失败,原因是：",e);
			return false;
		}
		
	}

	/**
	 * 获取词性分类标签信息
	 */
	@Override
	@TargetDataSource(name="third")
	public List<KeywordTypeEntity> getLableInfo() {
		return (List<KeywordTypeEntity>) ktp.findAll();
	}

	/**
	 * 根据id获取词的属性信息
	 * @param id
	 * @return
	 */
	@Override
	@TargetDataSource(name="third")
	public JSONObject findAttributeInfoById(String id) {
		try {
			long _id = Long.parseLong(id);
			
			return null;
		} catch (Exception e) {
			LOGGER.error("根据id查看词属性失败,原因是：",e);
			return null;
		}
	}

	/**
	 * 更新词性信息
	 */
	@Override
	@TargetDataSource(name="third")
	public boolean updateTypeWord(String typeWord) {
		try {
			KeywordTypeEntity ent = new KeywordTypeEntity();
			ent.setTypeWord(typeWord);
			ktp.save(ent);
			return true;
		} catch (Exception e) {
			LOGGER.error("添加词性失败,原因是：",e);
			return  false;
		}
	}
	/**
	 * 保存第一页数据
	 */
	@Override
	@TargetDataSource(name="third")
	public boolean saveOrUpAttributeData(AttributeDTO dto) {
		Long typeId = dto.getTypeId();
		String 	_wordNumber ="";
		try {
			if(typeId==0){
				String typeWord = dto.getTypeWord();
				if(StringUtil.isEmpty(typeWord)){
					return false;
				}
				KeywordTypeEntity ent = new KeywordTypeEntity();
				ent.setTypeWord(typeWord);
				KeywordTypeEntity save = ktp.save(ent);
				
				//根据类型id,判断分类编号属于A,B,C,D,E。。。。。等
				//不是新增类型，则一定存在，只需获取数量值就行
				updateDataSave(dto, save.getId(), _wordNumber);
				return true;
			}else{
				updateDataSave(dto, typeId, _wordNumber);
				return false;
			}
		} catch (Exception e) {
			LOGGER.error("添加词时报错,原因是：",e);
			return false;
		}
	}
	
	@TargetDataSource(name="third")
	private void updateDataSave(AttributeDTO dto,Long typeId,String  _wordNumber) {
		//根据类型id获取当前类型的数量
		Long count = kip.getCount(typeId);
		if(typeId>26){
			//随机取两个值进行匹对
			String ss = StringUtil.LC_FIRST[(typeId.intValue()-1)/10];//取整
			String s = StringUtil.LC_FIRST[(typeId.intValue()-1)%10];//取余
			_wordNumber = ss+ s+(count+1);
		}else{
			String ss = StringUtil.LC_FIRST[(typeId.intValue()-1)];
			_wordNumber = ss+(count+1);
		}
		//增加词
		ThesaurusEntity emt = rep.findByKeyword(dto.getKeyword());
		if(emt == null){
			ThesaurusEntity tity = new ThesaurusEntity();
			tity.setKeyword(dto.getKeyword());
			tity.setDescribe(dto.getDescribe());
			tity.setInsertTime(new Date());
			emt = rep.save(tity);
		}
		Long keywordId = emt.getId();
		//增加编号
		KeywordInfoEntity info = kip.findByWordId(emt.getId());
		if(info == null){
			KeywordInfoEntity entity = new KeywordInfoEntity();
			entity.setInsertTime(emt.getInsertTime());
			entity.setTypeId(typeId);
			entity.setWordId(emt.getId());
			entity.setWordNumber(_wordNumber);
			kip.save(entity);
		}
		//增加属性值		
		List<AttributeInfo> msg = dto.getMsg();
		List<AttributeEntity> list = new ArrayList<AttributeEntity>();
		msg.forEach(action->{
			AttributeEntity into = new AttributeEntity();
			into.setAttributeName(action.getAttributeName());
			into.setAttributeValue(action.getAttributeValue());
			into.setWordId(keywordId);
			list.add(into);
		});
		arp.save(list);
	}

	/**
	 * 根据类型词获取所有的关键词
	 * @param typeWord
	 */
	@Override
	@TargetDataSource(name="third")
	public List<ThesaurusEntity> findKeyWordByType(String typeWord) {
		try {
			long _typeId = Long.parseLong(typeWord);
			List<Integer> _ids = kip.getWordIdByTypeId(_typeId);
			List<ThesaurusEntity> list = new ArrayList<ThesaurusEntity>();
			_ids.forEach(action->{
				System.out.println("action的值>>>>>>>>>>>>>>>>"+action.longValue());
				ThesaurusEntity one = rep.findOne(action.longValue());
				list.add(one);
			});
			return list;
		} catch (Exception e) {
			LOGGER.error("根据类型词获取所有的关键词失败,原因是：",e);
			return null;
		}
	}

	/**
	 * 新增关系词
	 */
	@Override
	@TargetDataSource(name="third")
	public boolean updateRelatedWord(String relatedWord) {
		RelatedWordEntity ent = new RelatedWordEntity();
		ent.setRelatetion(relatedWord);
		try {
			RelatedWordEntity entity = kwp.findByRelatetion(relatedWord);
			if(entity==null){
				kwp.save(ent);
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("新增关系词失败,原因是：",e);
			return false;
		}
		
	}

	/**
	 * 获取所有的关系词
	 */
	@Override
	@TargetDataSource(name="third")
	public List<RelatedWordEntity> getAllRelatedInfo() {
		try {
			Iterable<RelatedWordEntity> findAll = kwp.findAll();
			
			return (List<RelatedWordEntity>)findAll ;
		} catch (Exception e) {
			LOGGER.error("获取所有的关系词失败,原因是：",e);
			return null;
		}
	}	
	
	
}

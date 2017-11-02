package com.huishu.ZSServer.repository.garden;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.huishu.ZSServer.entity.dto.GardenMapDTO;
import com.huishu.ZSServer.entity.garden.GardenMap;

public interface GardenMapRepositroy extends CrudRepository<GardenMap, Long> {

	/** 地图中按产业查gdp */
	@Query(value = "from GardenMap g where g.industry = ?1 and g.year in ?2 group by g.province")
	List<GardenMap> findGdp(String industry, Integer[] year);

	/** 地图中按产业查gdp2 */
	@Query(value = "from GardenMap g where g.industry = ?1 and g.province = ?2 and g.year in ?3")
	List<GardenMap> findGdp(String industry, String province, Integer[] year);

}
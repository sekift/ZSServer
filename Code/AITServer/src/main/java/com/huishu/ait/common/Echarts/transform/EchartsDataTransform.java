package com.huishu.ait.common.Echarts.transform;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huishu.ait.common.Echarts.dataSource.Transformer;

/**
 * @author hhy
 * @date 2017年7月27日
 * @Parem
 * @return 
 * 
 */
@Component("echartsTransformer")
public class EchartsDataTransform  implements Transformer {
   
	
	/**
	 * 将从后台获取的词云数据，转换为echarts识别的数据格式，以及添加词云的文字样式
	 * 
	 * @param data
	 * @return
	 */
	public JSONArray transformWordCloud(JSONArray data) {
		JSONArray arrayRe = new JSONArray();
		if (null != data) {
			for (int i = 0; i < data.size(); i++) {
				JSONObject o = (JSONObject) data.get(i);
				int value = Integer.parseInt(o.get("heat") + "");
				String itemStyle = "{name:'" + o.get("word") + "',value:'" + value * 100
						+ "',itemStyle:{normal: {color: 'rgb(" + Math.round(Math.random() * 160) + ","
						+ Math.round(Math.random() * 160) + "," + Math.round(Math.random() * 160) + ")'}}}";
				JSONObject obj = JSONObject.parseObject(itemStyle);
				arrayRe.add(obj);
			}
		}
		return arrayRe;
	}

	
	@Override
	public Object transform(String data, String... params) {
		return null;
	}
	
}

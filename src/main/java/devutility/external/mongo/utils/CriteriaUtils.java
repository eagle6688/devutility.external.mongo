package devutility.external.mongo.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * CriteriaUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 15:59:32
 */
public class CriteriaUtils {
	public static Criteria or(List<Query> queries) {
		Criteria criteria = new Criteria();
		Map<String, Query> map = new LinkedHashMap<>();

		for (Query query : queries) {
			map.put(query.getQueryObject().toJson(), query);
		}

		return criteria;
	}
}
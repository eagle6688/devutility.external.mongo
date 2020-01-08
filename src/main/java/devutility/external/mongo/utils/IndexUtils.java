package devutility.external.mongo.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.index.Index;

/**
 * 
 * IndexUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-07 11:33:07
 */
public class IndexUtils {
	/**
	 * Create a Index object.
	 * @param indexMap Index map, format: field, Direction.
	 * @param unique Unique index or not?
	 * @return Index
	 */
	public static Index create(Map<String, Direction> indexMap, boolean unique) {
		Index index = new Index();

		for (Entry<String, Direction> entry : indexMap.entrySet()) {
			index.on(entry.getKey(), entry.getValue());
		}

		if (unique) {
			return index.unique();
		}

		return index;
	}
}
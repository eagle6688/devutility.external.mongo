package devutility.external.mongo;

import java.lang.reflect.Field;

import org.springframework.data.annotation.Id;

import devutility.internal.lang.StringUtils;

/**
 * 
 * MongoUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-01 22:13:52
 */
public class MongoUtils {
	/**
	 * Get field name in MongoDB.
	 * @param field Field object.
	 * @return String
	 */
	public static String getFieldName(Field field) {
		if (field.getAnnotation(Id.class) != null) {
			return "_id";
		}

		org.springframework.data.mongodb.core.mapping.Field aField = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);

		if (aField != null) {
			if (StringUtils.isNotEmpty(aField.value())) {
				return aField.value();
			}

			if (StringUtils.isNotEmpty(aField.name())) {
				return aField.name();
			}
		}

		return field.getName();
	}
}
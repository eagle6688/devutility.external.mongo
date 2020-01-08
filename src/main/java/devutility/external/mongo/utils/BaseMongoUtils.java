package devutility.external.mongo.utils;

import java.lang.reflect.Field;

import org.springframework.data.annotation.Id;

import devutility.internal.lang.StringUtils;

/**
 * 
 * BaseMongoUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 14:50:54
 */
public abstract class BaseMongoUtils {
	/**
	 * Get field name in MongoDB.
	 * @param field Field object.
	 * @return String
	 */
	public static String getFieldName(Field field) {
		org.springframework.data.mongodb.core.mapping.Field aField = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);

		if (aField != null) {
			if (StringUtils.isNotEmpty(aField.value())) {
				return aField.value();
			}

			if (StringUtils.isNotEmpty(aField.name())) {
				return aField.name();
			}
		}

		if (field.getAnnotation(Id.class) != null) {
			return "_id";
		}

		return field.getName();
	}
}
package devutility.external.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.StringUtils;
import devutility.internal.lang.models.EntityField;

/**
 * 
 * MongoScriptUtils
 * 
 * @author: Aldwin Su
 * @version: 2019-12-06 17:54:30
 */
public class MongoScriptUtils {
	private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");

	private final static SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	public static String getSaveScript(Object entity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		StringBuffer buffer = new StringBuffer();
		String collectionName = getCollectionName(entity.getClass());

		if (StringUtils.isNullOrEmpty(collectionName)) {
			throw new IllegalArgumentException("Illegal Document object.");
		}

		List<EntityField> entityFields = ClassUtils.getEntityFields(entity.getClass());

		for (EntityField entityField : entityFields) {
			Field field = entityField.getField();
			Method getter = entityField.getGetter();
			Object value = getter.invoke(entity);

			if (value == null) {
				continue;
			}

			String fieldName = getFieldName(field);

			if (value instanceof Date) {
				buffer.append(String.format("\"%s\" : ISODate(\"%s\"), ", fieldName, getDateString((Date) value)));
				continue;
			}

			if (value instanceof Integer || value instanceof Long) {
				buffer.append(String.format("\"%s\" : %d, ", fieldName, value));
				continue;
			}

			if (value instanceof Double || value instanceof Float) {
				buffer.append(String.format("\"%s\" : %f, ", fieldName, value));
				continue;
			}

			buffer.append(String.format("\"%s\" : \"%s\", ", fieldName, value.toString()));
		}

		if (buffer.length() > 0) {
			buffer = buffer.deleteCharAt(buffer.length() - 2);
			buffer.insert(0, String.format("db.getCollection('%s').save({", collectionName));
			buffer.append("});");
		}

		return buffer.toString();
	}

	public static String getCollectionName(Class<?> clazz) {
		Document document = clazz.getAnnotation(Document.class);

		if (document == null) {
			return null;
		}

		return document.collection();
	}

	public static String getFieldName(Field field) {
		org.springframework.data.mongodb.core.mapping.Field fieldA = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);

		if (fieldA == null) {
			org.springframework.data.annotation.Id idField = field.getAnnotation(org.springframework.data.annotation.Id.class);

			if (idField != null) {
				return "_id";
			}

			return field.getName();
		}

		return fieldA.value();
	}

	public static String getDateString(Date date) {
		return String.format("%sT%sZ", SIMPLE_DATE_FORMAT.format(date), SIMPLE_TIME_FORMAT.format(date));
	}
}
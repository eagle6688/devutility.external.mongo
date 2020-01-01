package devutility.external.mongo;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Update;

import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.StringUtils;
import devutility.internal.lang.models.EntityField;

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

	public static Update objectToUpdate(Object entity, List<EntityField> entityFields, boolean containNullValue) throws ReflectiveOperationException {
		if (entityFields == null) {
			entityFields = ClassUtils.getEntityFields(entity.getClass());
		}

		Update update = new Update();

		for (EntityField entityField : entityFields) {
			Object fieldValue = entityField.getValue(entity);

			if (!containNullValue && fieldValue == null) {
				continue;
			}

			String fieldName = getFieldName(entityField.getField());
			update.set(fieldName, fieldValue);
		}

		return update;
	}

	public static Update objectToUpdate(Object entity, List<EntityField> entityFields) throws ReflectiveOperationException {
		return objectToUpdate(entity, entityFields, true);
	}
}
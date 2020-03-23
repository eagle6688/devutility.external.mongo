package devutility.external.mongo.utils;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import devutility.internal.lang.ClassUtils;
import devutility.internal.model.EntityField;

/**
 * 
 * UpdateUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 14:51:58
 */
public class UpdateUtils extends BaseMongoUtils {
	/**
	 * Convert entity to Update object.
	 * @param entity MongoDB entity.
	 * @param entityFields EntityField objects.
	 * @param containNullValue Whether contain field with null value in entity? The result does not contain fileds with null
	 *            value if set containNullValue=false.
	 * @return Update
	 * @throws ReflectiveOperationException from getValue method.
	 */
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

	/**
	 * Convert entity to Update object.
	 * @param entity MongoDB entity.
	 * @param entityFields EntityField objects.
	 * @return Update
	 * @throws ReflectiveOperationException from getValue method.
	 */
	public static Update objectToUpdate(Object entity, List<EntityField> entityFields) throws ReflectiveOperationException {
		return objectToUpdate(entity, entityFields, true);
	}
}
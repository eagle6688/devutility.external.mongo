package devutility.external.mongo;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

	/**
	 * Convert entity to Query object.
	 * @param entity MongoDB entity.
	 * @param entityFields EntityField objects.
	 * @param containNullValue Whether contain field with null value in entity? The result does not contain fileds with null
	 *            value if set containNullValue=false.
	 * @return Query
	 * @throws ReflectiveOperationException
	 */
	public static Query objectToQuery(Object entity, List<EntityField> entityFields, boolean containNullValue) throws ReflectiveOperationException {
		if (entityFields == null) {
			entityFields = ClassUtils.getEntityFields(entity.getClass());
		}

		Query query = new Query();

		for (EntityField entityField : entityFields) {
			Object fieldValue = entityField.getValue(entity);

			if (!containNullValue && fieldValue == null) {
				continue;
			}

			String fieldName = getFieldName(entityField.getField());

			if (Collection.class.isAssignableFrom(fieldValue.getClass()) || fieldValue.getClass().isArray()) {
				query.addCriteria(Criteria.where(fieldName).in(fieldValue));
			} else {
				query.addCriteria(Criteria.where(fieldName).is(fieldValue));
			}
		}

		return query;
	}

	/**
	 * Convert entity to Query object.
	 * @param entity MongoDB entity.
	 * @param entityFields EntityField objects.
	 * @return Query
	 * @throws ReflectiveOperationException
	 */
	public static Query objectToQuery(Object entity, List<EntityField> entityFields) throws ReflectiveOperationException {
		return objectToQuery(entity, entityFields, true);
	}
}
package devutility.external.mongo.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.models.EntityField;

/**
 * 
 * QueryUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 14:49:05
 */
public class QueryUtils extends BaseMongoUtils {
	/**
	 * Convert entity to Query object.
	 * @param entity MongoDB entity.
	 * @param entityFields EntityField objects.
	 * @param containNullValue Whether contain field with null value in entity? The result does not contain fileds with null
	 *            value if set containNullValue=false.
	 * @return Query
	 * @throws ReflectiveOperationException from getValue method.
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

			if (fieldValue != null && (Collection.class.isAssignableFrom(fieldValue.getClass()) || fieldValue.getClass().isArray())) {
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
	 * @throws ReflectiveOperationException from getValue method.
	 */
	public static Query objectToQuery(Object entity, List<EntityField> entityFields) throws ReflectiveOperationException {
		return objectToQuery(entity, entityFields, true);
	}

	/**
	 * Convert entity to Query object.
	 * @param entity MongoDB entity.
	 * @param fields Fields need in Query object.
	 * @return Query
	 * @throws ReflectiveOperationException from getValue method.
	 */
	public static Query objectToQueryByFields(Object entity, Collection<String> fields) throws ReflectiveOperationException {
		List<EntityField> entityFields = ClassUtils.getIncludedEntityFields(fields, entity.getClass());
		return objectToQuery(entity, entityFields, true);
	}
}
package devutility.external.mongo;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

import com.mongodb.bulk.BulkWriteResult;

import devutility.internal.lang.ClassHelper;
import devutility.internal.lang.models.EntityField;
import devutility.internal.lang.models.EntityFieldUtils;

public class BulkOperationsHelper {
	/**
	 * MongoOperations
	 */
	private MongoOperations mongoOperations;

	/**
	 * Constructor
	 * @param mongoOperations: MongoOperations object
	 */
	public BulkOperationsHelper(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	/**
	 * Get BulkOperations object
	 * @param bulkMode: Mode for bulk operation.
	 * @param clazz: MongoDB collection entity class
	 * @return BulkOperations
	 */
	private BulkOperations bulkOperations(BulkMode bulkMode, Class<?> clazz) {
		return mongoOperations.bulkOps(bulkMode, clazz);
	}

	/**
	 * Entity to query and update pair.
	 * @param entity: Entity object
	 * @param entityFields: Entity fields
	 * @return {@literal Pair<Query, Update>}
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public static <T> Pair<Query, Update> toPair(T entity, List<EntityField> entityFields) throws IllegalArgumentException, ReflectiveOperationException {
		Query query = new Query();
		Update update = new Update();

		for (EntityField entityField : entityFields) {
			boolean hasAnnotation = false;
			Object value = entityField.getValue(entity);
			Annotation[] annotations = entityField.getField().getAnnotations();

			for (Annotation annotation : annotations) {
				if (MongoDbUtils.isField(annotation)) {
					hasAnnotation = true;
					update.set(MongoDbUtils.getFieldName(annotation), value);
					break;
				}

				if (MongoDbUtils.isPk(annotation)) {
					query.addCriteria(Criteria.where("_id").is(value));
					update.set("_id", value);
					hasAnnotation = true;
					break;
				}
			}

			if (!hasAnnotation) {
				update.set(entityField.getField().getName(), value);
			}
		}

		return Pair.of(query, update);
	}

	/**
	 * Entities to query and update pairs.
	 * @param list: Entities.
	 * @param clazz: Entity class.
	 * @return {@literal List<Pair<Query,Update>>}
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public static <T> List<Pair<Query, Update>> toPairs(List<T> list, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		List<EntityField> entityFields = ClassHelper.getEntityFields(clazz);
		return toPairs(list, entityFields, entityFields);
	}

	/**
	 * Entities to query and update pairs.
	 * @param list: Entities.
	 * @param fieldsForQuery: Fields contains in query.
	 * @param clazz: Entity class.
	 * @return {@literal List<Pair<Query,Update>>}
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public static <T> List<Pair<Query, Update>> toPairs(List<T> list, List<String> fieldsForQuery, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		List<EntityField> entityFields = ClassHelper.getEntityFields(clazz);
		List<EntityField> entityFieldsForQuery = EntityFieldUtils.includeEntityFields(entityFields, fieldsForQuery);
		return toPairs(list, entityFieldsForQuery, entityFields);
	}

	/**
	 * Entities to query and update pairs.
	 * @param list: Entities.
	 * @param entityFieldsForQuery: EntityField for query.
	 * @param entityFieldsForUpdate: EntityField for update.
	 * @return {@code List<Pair<Query,Update>>}
	 * @throws ReflectiveOperationException
	 */
	public static <T> List<Pair<Query, Update>> toPairs(List<T> list, List<EntityField> entityFieldsForQuery, List<EntityField> entityFieldsForUpdate) throws ReflectiveOperationException {
		List<Pair<Query, Update>> pairs = new ArrayList<>(list.size());

		for (T entity : list) {
			Query query = MongoDbUtils.entityToQuery(entity, entityFieldsForQuery);
			Update update = MongoDbUtils.entityToUpdate(entity, entityFieldsForUpdate);
			pairs.add(Pair.of(query, update));
		}

		return pairs;
	}

	/**
	 * Save list
	 * @param list: Entities.
	 * @param clazz: Entity class.
	 * @return BulkWriteResult
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public <T> BulkWriteResult save(List<T> list, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		BulkOperations bulkOperations = bulkOperations(BulkMode.UNORDERED, clazz);
		List<Pair<Query, Update>> pairs = toPairs(list, clazz);
		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}

	/**
	 * Save list
	 * @param list: Entities.
	 * @param fieldsForQuery: Fields can determine an unique entity.
	 * @param clazz: Entity class.
	 * @return BulkWriteResult
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public <T> BulkWriteResult save(List<T> list, List<String> fieldsForQuery, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		BulkOperations bulkOperations = bulkOperations(BulkMode.UNORDERED, clazz);
		List<Pair<Query, Update>> pairs = toPairs(list, fieldsForQuery, clazz);
		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}
}
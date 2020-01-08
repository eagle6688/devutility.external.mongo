package devutility.external.mongo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import devutility.external.mongo.utils.IndexUtils;
import devutility.internal.util.CollectionUtils;

/**
 * 
 * MongoHelper
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 14:46:03
 */
public class MongoHelper extends BaseMongoHelper {
	/**
	 * Constructor
	 * @param mongoOperations MongoOperations object.
	 */
	public MongoHelper(MongoOperations mongoOperations) {
		super(mongoOperations);
	}

	/**
	 * Return a distinct list from MongoDB with provided Query object.
	 * @param query Query object.
	 * @param field Query field name.
	 * @param entityClass Class object for entity.
	 * @param resultClass Class object for result.
	 * @return {@code List<R>}
	 */
	public <E, R> List<R> distinct(Query query, String field, Class<E> entityClass, Class<R> resultClass) {
		if (!"_id".equals(field)) {
			query.fields().exclude("_id").include(field);
		}

		return mongoOperations.findDistinct(query, field, entityClass, resultClass);
	}

	/**
	 * Find one value for the provided field with provided Query object.
	 * @param query Query object make sure the result for this would be unique.
	 * @param field The field need query.
	 * @param entityClass Class object for entity.
	 * @param resultClass Class object for result.
	 * @return {@code R} The only value for query and field.
	 */
	public <E, R> R findOneValue(Query query, String field, Class<E> entityClass, Class<R> resultClass) {
		List<R> list = distinct(query, field, entityClass, resultClass);

		if (CollectionUtils.isNullOrEmpty(list)) {
			return null;
		}

		if (list.size() > 1) {
			throw new RuntimeException("There are more than one different values in Mongo returned result.");
		}

		return list.get(0);
	}

	/**
	 * Create index in MongoDB.
	 * @param indexMap Index map, format: field, Direction
	 * @param unique Unique index or not?
	 * @param entityClass Collection Class object for entity.
	 * @return String
	 */
	public String createIndex(Map<String, Direction> indexMap, boolean unique, Class<?> entityClass) {
		Index index = IndexUtils.create(indexMap, unique);
		return mongoOperations.indexOps(entityClass).ensureIndex(index);
	}

	/**
	 * Create index in MongoDB.
	 * @param fields Fields need to be indexed.
	 * @param unique Unique index or not?
	 * @param entityClass Collection Class object for entity.
	 */
	public String createIndex(Set<String> fields, boolean unique, Class<?> entityClass) {
		Map<String, Direction> indexMap = new LinkedHashMap<>();

		for (String field : fields) {
			indexMap.put(field, Direction.ASC);
		}

		return createIndex(indexMap, unique, entityClass);
	}

	/**
	 * Create index in MongoDB.
	 * @param field Field need to be indexed.
	 * @param unique Unique index or not?
	 * @param entityClass Collection Class object for entity.
	 */
	public void createIndex(String field, boolean unique, Class<?> entityClass) {
		createIndex(new HashSet<>(Arrays.asList(field)), unique, entityClass);
	}

	/**
	 * Update data in MongoDB.
	 * @param id MongoDB primary key value.
	 * @param versionField Version field.
	 * @param versionValue Version value.
	 * @param setField Field need update.
	 * @param setValue Field value need update.
	 * @param clazz Collection entity class.
	 * @return UpdateResult
	 */
	public UpdateResult update(String id, String versionField, Object versionValue, String setField, Object setValue, Class<?> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		query.addCriteria(Criteria.where(versionField).is(versionValue));

		Update update = new Update();
		update.set(setField, setValue);

		return mongoOperations.updateMulti(query, update, clazz);
	}
}
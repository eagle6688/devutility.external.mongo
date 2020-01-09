package devutility.external.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

import com.mongodb.bulk.BulkWriteResult;

import devutility.external.mongo.utils.PairUtils;
import devutility.internal.lang.reflect.GenericTypeUtils;

/**
 * 
 * MongoBulkHelper
 * 
 * @author: Aldwin Su
 * @version: 2020-01-07 18:10:16
 */
public class MongoBulkHelper extends BaseMongoHelper {
	/**
	 * BulkMode
	 */
	private BulkMode bulkMode;

	/**
	 * Constructor
	 * @param mongoOperations MongoOperations object.
	 * @param bulkMode BulkMode enum.
	 */
	public MongoBulkHelper(MongoOperations mongoOperations, BulkMode bulkMode) {
		super(mongoOperations);
		this.bulkMode = bulkMode;
	}

	/**
	 * Constructor
	 * @param mongoOperations MongoOperations object.
	 */
	public MongoBulkHelper(MongoOperations mongoOperations) {
		this(mongoOperations, BulkMode.UNORDERED);
	}

	/**
	 * Use provided queryFields to query each entity, for entities exist in Mongo, this method would update the provided
	 * updateFields with entity value; for entities not exist in Mongo, this method would insert the new entity.
	 * @param entities Objects need save into Mongo.
	 * @param queryFields Fields for query.
	 * @param updateFields Fields for update.
	 * @return BulkWriteResult
	 * @throws ReflectiveOperationException from objectsToPairs.
	 */
	public BulkWriteResult upsert(List<?> entities, String[] queryFields, String[] updateFields) throws ReflectiveOperationException {
		Class<?> clazz = GenericTypeUtils.getGenericClass(entities);

		if (clazz == null) {
			return null;
		}

		BulkOperations bulkOperations = mongoOperations.bulkOps(bulkMode, clazz);
		List<Pair<Query, Update>> pairs = PairUtils.objectsToPairs(entities, queryFields, updateFields);
		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}

	/**
	 * Use provided queryFields to query each entity, for entities exist in Mongo, this method would update the provided
	 * updateFields with entity value; for entities not exist in Mongo, this method would insert the new entity.
	 * @param entities Objects need save into Mongo.
	 * @param queryFields Fields for query.
	 * @return BulkWriteResult
	 * @throws ReflectiveOperationException from upsert method.
	 */
	public BulkWriteResult upsert(List<?> entities, String[] queryFields) throws ReflectiveOperationException {
		return upsert(entities, queryFields, null);
	}

	/**
	 * Use provided queryFields to query each entity and update the provided updateFields with entity value.
	 * @param entities Objects need save into Mongo.
	 * @param queryFields Fields for query.
	 * @param updateFields Fields for update.
	 * @return BulkWriteResult
	 * @throws ReflectiveOperationException from objectsToPairs method.
	 */
	public BulkWriteResult update(List<?> entities, String[] queryFields, String[] updateFields) throws ReflectiveOperationException {
		Class<?> clazz = GenericTypeUtils.getGenericClass(entities);

		if (clazz == null) {
			return null;
		}

		BulkOperations bulkOperations = mongoOperations.bulkOps(bulkMode, clazz);
		List<Pair<Query, Update>> pairs = PairUtils.objectsToPairs(entities, queryFields, updateFields);
		bulkOperations.updateMulti(pairs);
		return bulkOperations.execute();
	}

	/**
	 * Use provided queryFields to query each entity and update the provided updateFields with entity value.
	 * @param entities Objects need save into Mongo.
	 * @param queryFields Fields for query.
	 * @return BulkWriteResult
	 * @throws ReflectiveOperationException from objectsToPairs method.
	 */
	public BulkWriteResult update(List<?> entities, String[] queryFields) throws ReflectiveOperationException {
		return update(entities, queryFields, null);
	}
}
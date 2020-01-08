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

	public BulkWriteResult upsert(List<?> entities, String[] queryFields) throws ReflectiveOperationException {
		return upsert(entities, queryFields, null);
	}

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

	public BulkWriteResult update(List<?> entities, String[] queryFields) throws ReflectiveOperationException {
		return update(entities, queryFields, null);
	}

	public BulkWriteResult save(List<?> entities, String[] queryFields, String[] updateFields) {
		Class<?> clazz = GenericTypeUtils.getGenericClass(entities);

		if (clazz == null) {
			return null;
		}

		BulkOperations bulkOperations = mongoOperations.bulkOps(bulkMode, clazz);
		return null;
	}

	/**
	 * Save list
	 * @param list Entities.
	 * @param clazz Entity class.
	 * @return BulkWriteResult
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public <T> BulkWriteResult save(List<T> list, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		BulkOperations bulkOperations = mongoOperations.bulkOps(BulkMode.UNORDERED, clazz);
		//		List<Pair<Query, Update>> pairs = toPairs(list, clazz);
		//		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}

	/**
	 * Save list
	 * @param list Entities.
	 * @param fieldsForQuery Fields can determine an unique entity.
	 * @param clazz Entity class.
	 * @return BulkWriteResult
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public <T> BulkWriteResult save(List<T> list, List<String> fieldsForQuery, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		BulkOperations bulkOperations = mongoOperations.bulkOps(BulkMode.UNORDERED, clazz);
		//		List<Pair<Query, Update>> pairs = toPairs(list, fieldsForQuery, clazz);
		//		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}

	/**
	 * Save list.
	 * @param list Entities.
	 * @param fieldsForQuery Fields can determine an unique entity.
	 * @param fieldsForUpdate Fields for update.
	 * @param clazz Entity class.
	 * @return BulkWriteResult
	 * @throws IllegalArgumentException From toPairs method.
	 * @throws ReflectiveOperationException From toPairs method.
	 */
	public <T> BulkWriteResult save(List<T> list, List<String> fieldsForQuery, List<String> fieldsForUpdate, Class<T> clazz) throws IllegalArgumentException, ReflectiveOperationException {
		BulkOperations bulkOperations = mongoOperations.bulkOps(BulkMode.UNORDERED, clazz);
		//		List<Pair<Query, Update>> pairs = toPairs(list, fieldsForQuery, fieldsForUpdate, clazz);
		//		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}
}
package devutility.external.mongo.helper;

import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.bulk.BulkWriteResult;

/**
 * 
 * MongoBulkHelper
 * 
 * @author: Aldwin Su
 * @version: 2020-01-07 18:10:16
 */
public class MongoBulkHelper {
	/**
	 * MongoOperations
	 */
	private MongoOperations mongoOperations;

	/**
	 * Constructor
	 * @param mongoOperations MongoOperations object
	 */
	public MongoBulkHelper(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	/**
	 * Get BulkOperations object
	 * @param bulkMode Mode for bulk operation.
	 * @param clazz MongoDB collection entity class
	 * @return BulkOperations
	 */
	private BulkOperations bulkOperations(BulkMode bulkMode, Class<?> clazz) {
		return mongoOperations.bulkOps(bulkMode, clazz);
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
		BulkOperations bulkOperations = bulkOperations(BulkMode.UNORDERED, clazz);
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
		BulkOperations bulkOperations = bulkOperations(BulkMode.UNORDERED, clazz);
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
		BulkOperations bulkOperations = bulkOperations(BulkMode.UNORDERED, clazz);
		//		List<Pair<Query, Update>> pairs = toPairs(list, fieldsForQuery, fieldsForUpdate, clazz);
		//		bulkOperations.upsert(pairs);
		return bulkOperations.execute();
	}
}
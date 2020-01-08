package devutility.external.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

/**
 * 
 * BaseMongoHelper
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 15:06:45
 */
public abstract class BaseMongoHelper {
	/**
	 * MongoOperations
	 */
	protected MongoOperations mongoOperations;

	/**
	 * Constructor
	 * @param mongoOperations MongoOperations object.
	 */
	public BaseMongoHelper(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
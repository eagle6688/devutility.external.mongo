package devutility.external.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

public abstract class BaseTest extends devutility.internal.test.BaseTest {
	protected MongoOperations mongoOperations = MongoDbUtils.mongoTemplate("dbconfig.properties", "mongodb");
}
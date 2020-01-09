package devutility.external.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

import devutility.external.mongo.config.DbConfig;

public abstract class BaseTestForMongo extends devutility.internal.test.BaseTest {
	protected MongoOperations mongoOperations = DbConfig.mongoDb();
}
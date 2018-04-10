package devutility.external.mongo;

import org.springframework.data.mongodb.core.MongoOperations;

import devutility.external.mongo.config.DbConfig;

public abstract class BaseTest extends devutility.internal.test.BaseTest {
	protected MongoOperations mongoOperations = DbConfig.mongoDb();
}
package devutility.external.mongo.config;

import org.springframework.data.mongodb.core.MongoTemplate;

import devutility.external.mongo.MongoDbUtils;
import devutility.internal.data.DbInstanceUtils;
import devutility.internal.models.DbInstance;

public class DbConfig {
	/**
	 * Configuration for database
	 */
	public static final String CONFIG_NAME = "dbconfig.properties";

	/**
	 * MongoDbHolder
	 */
	private static class MongoDbHolder {
		private static DbInstance dbInstance = DbInstanceUtils.getInstance(CONFIG_NAME, "mongodb");
		public static MongoTemplate mongoTemplate = MongoDbUtils.mongoTemplate(dbInstance);
	}

	/**
	 * mongoDb
	 * @returnMongoTemplate
	 */
	public static MongoTemplate mongoDb() {
		return MongoDbHolder.mongoTemplate;
	}
}
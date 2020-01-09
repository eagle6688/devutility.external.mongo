package devutility.external.mongo.config;

import org.springframework.data.mongodb.core.MongoTemplate;

import devutility.external.mongo.utils.MongoTemplateUtils;
import devutility.internal.util.PropertiesUtils;

public class DbConfig {
	/**
	 * MongoDbHolder
	 */
	private static class MongoDbHolder {
		private static String MONGO_URI = PropertiesUtils.getPropertyFromResource("dbconfig.properties", "mongodb.uri");
		public static MongoTemplate mongoTemplate = MongoTemplateUtils.mongoTemplate(MONGO_URI);
	}

	/**
	 * mongoDb
	 * @returnMongoTemplate
	 */
	public static MongoTemplate mongoDb() {
		return MongoDbHolder.mongoTemplate;
	}
}
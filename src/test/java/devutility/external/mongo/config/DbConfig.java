package devutility.external.mongo.config;

import java.io.IOException;

import org.springframework.data.mongodb.core.MongoTemplate;

import devutility.external.mongo.utils.MongoTemplateUtils;
import devutility.internal.util.PropertiesUtils;

public class DbConfig {
	/**
	 * MongoDbHolder
	 */
	private static class MongoDbHolder {
		private static String MONGO_URI = null;
		public static MongoTemplate mongoTemplate = null;

		static {
			try {
				MONGO_URI = PropertiesUtils.getValueFromResource("dbconfig.properties", "mongodb.uri");
			} catch (IOException e) {
				e.printStackTrace();
			}

			mongoTemplate = MongoTemplateUtils.mongoTemplate(MONGO_URI);
		}
	}

	/**
	 * mongoDb
	 * @returnMongoTemplate
	 */
	public static MongoTemplate mongoDb() {
		return MongoDbHolder.mongoTemplate;
	}
}
package devutility.external.mongo;

import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * 
 * MongoTemplateUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-06 22:12:19
 */
public class MongoTemplateUtils {
	/**
	 * Create a MongoTemplate object.
	 * @param mongoClientSettings MongoClientSettings object.
	 * @param databaseName Database name.
	 * @param converters Converter objects.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(MongoClientSettings mongoClientSettings, String databaseName, List<?> converters) {
		MongoClient mongoClient = MongoClients.create(mongoClientSettings);
		MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(mongoClient, databaseName);
		MongoConverter mongoConverter = MongoConverterUtils.mongoConverter(mongoDbFactory, converters);

		if (mongoConverter == null) {
			mongoConverter = MongoConverterUtils.defaultMongoConverter(mongoDbFactory);
		}

		return new MongoTemplate(mongoDbFactory, mongoConverter);
	}

	/**
	 * Create a MongoTemplate object.
	 * @param connectionUri MongoDB connection string.
	 * @param converters Converter objects.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(String connectionUri, List<Object> converters) {
		ConnectionString connectionString = new ConnectionString(connectionUri);
		MongoClientSettings mongoClientSettings = MongoClientSettingsUtils.create(connectionString);
		return mongoTemplate(mongoClientSettings, connectionString.getDatabase(), converters);
	}

	/**
	 * Create a MongoTemplate object.
	 * @param connectionUri MongoDB connection string.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(String connectionUri) {
		return mongoTemplate(connectionUri, null);
	}
}
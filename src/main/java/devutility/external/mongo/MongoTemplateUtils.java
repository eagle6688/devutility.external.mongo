package devutility.external.mongo;

import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;

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
	 * @param uri MongoDB connection uri.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(String uri) {
		MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(uri);
		MongoConverter mongoConverter = MongoConverterUtils.defaultMongoConverter(mongoDbFactory);
		return new MongoTemplate(mongoDbFactory, mongoConverter);
	}

	/**
	 * Create a MongoTemplate object.
	 * @param uri MongoDB connection uri.
	 * @param converters Customer converters.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(String uri, List<Object> converters) {
		MongoDbFactory mongoDbFactory = new SimpleMongoClientDbFactory(uri);
		MongoConverter mongoConverter = MongoConverterUtils.mongoConverter(mongoDbFactory, converters);
		return new MongoTemplate(mongoDbFactory, mongoConverter);
	}
}
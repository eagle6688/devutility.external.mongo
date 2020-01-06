package devutility.external.mongo;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * 
 * MongoConverterUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-06 22:10:23
 */
public class MongoConverterUtils {
	/**
	 * Create a default MongoConverter object.
	 * @param mongoDbFactory MongoDbFactory object.
	 * @return MongoConverter
	 */
	public static MongoConverter defaultMongoConverter(MongoDbFactory mongoDbFactory) {
		return mongoConverter(mongoDbFactory, Collections.emptyList());
	}

	/**
	 * Create a MongoConverter object.
	 * @param mongoDbFactory MongoDbFactory object.
	 * @param converters Customer converters.
	 * @return MongoConverter
	 */
	public static MongoConverter mongoConverter(MongoDbFactory mongoDbFactory, List<?> converters) {
		if (mongoDbFactory == null) {
			return null;
		}

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MongoCustomConversions conversions = new MongoCustomConversions(converters);

		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
		mappingContext.afterPropertiesSet();

		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
		converter.setCustomConversions(conversions);
		converter.afterPropertiesSet();
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}
}
package devutility.external.mongo.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

/**
 * 
 * MongoClientSettingsUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-07 10:46:26
 */
public class MongoClientSettingsUtils {
	/**
	 * Create a MongoClientSettings object use provided ConnectionString object.
	 * @param connectionString ConnectionString object.
	 * @return MongoClientSettings
	 */
	public static MongoClientSettings create(ConnectionString connectionString) {
		MongoClientSettings.Builder builder = MongoClientSettings.builder();
		builder.applyConnectionString(connectionString);
		return builder.build();
	}
}
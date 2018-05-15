package devutility.external.mongo;

import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import devutility.internal.base.SingletonFactory;
import devutility.internal.dao.DbInstanceHelper;
import devutility.internal.dao.models.DbInstance;
import devutility.internal.lang.models.EntityField;
import devutility.internal.util.CollectionUtils;

public class MongoDbUtils {
	/**
	 * Create MongoClient object
	 * @param dbInstance: DbInstance object
	 * @return MongoClient
	 * @throws UnknownHostException
	 */
	public static MongoClient mongoClient(DbInstance dbInstance) throws UnknownHostException {
		if (dbInstance == null) {
			return null;
		}

		MongoCredential mongoCredential = createMongoCredential(dbInstance);
		ServerAddress serverAddress = createServerAddress(dbInstance);
		return mongoClient(serverAddress, mongoCredential);
	}

	/**
	 * Create MongoClient object
	 * @param serverAddress: ServerAddress object
	 * @param mongoCredential: MongoCredential object
	 * @return MongoClient
	 */
	public static MongoClient mongoClient(ServerAddress serverAddress, MongoCredential mongoCredential) {
		if (serverAddress == null) {
			return null;
		}

		if (mongoCredential == null) {
			return new MongoClient(serverAddress);
		}

		return new MongoClient(serverAddress, Arrays.asList(mongoCredential));
	}

	/**
	 * Create MongoTemplate object
	 * @param dbInstance: DbInstance object
	 * @return MongoTemplate
	 * @throws UnknownHostException
	 */
	public static MongoTemplate mongoTemplate(DbInstance dbInstance) throws UnknownHostException {
		if (dbInstance == null) {
			return null;
		}

		MongoDbFactory mongoDbFactory = mongoDbFactory(dbInstance);
		MongoConverter mongoConverter = defaultMongoConverter(mongoDbFactory);
		return new MongoTemplate(mongoDbFactory, mongoConverter);
	}

	/**
	 * Get or create a MongoTemplate instance.
	 * @param propertiesFile: Properties file
	 * @param prefix: Prefix of mongodb config in properties file.
	 * @return MongoTemplate
	 */
	public static MongoTemplate mongoTemplate(String propertiesFile, String prefix) {
		String key = String.format("%s.%s", prefix, MongoTemplate.class.getName());
		MongoTemplate mongoTemplate = SingletonFactory.get(key, MongoTemplate.class);

		if (mongoTemplate != null) {
			return mongoTemplate;
		}

		synchronized (MongoDbUtils.class) {
			if (mongoTemplate == null) {
				DbInstance dbInstance = DbInstanceHelper.getInstance(propertiesFile, prefix);

				try {
					mongoTemplate = SingletonFactory.save(key, mongoTemplate(dbInstance));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}

		return mongoTemplate;
	}

	/**
	 * Create MongoCredential object
	 * @param dbInstance: DbInstance object
	 * @return MongoCredential
	 */
	private static MongoCredential createMongoCredential(DbInstance dbInstance) {
		if (dbInstance == null || dbInstance.getLoginName() == null) {
			return null;
		}

		return MongoCredential.createCredential(dbInstance.getLoginName(), dbInstance.getDatabase(), dbInstance.getPassword().toCharArray());
	}

	/**
	 * Create ServerAddress object
	 * @param dbInstance: Database instance
	 * @return ServerAddress
	 * @throws UnknownHostException
	 */
	private static ServerAddress createServerAddress(DbInstance dbInstance) throws UnknownHostException {
		if (dbInstance == null) {
			return null;
		}

		return new ServerAddress(dbInstance.getHost(), dbInstance.getPort());
	}

	/**
	 * Create MongoDbFactory object
	 * @param dbInstance: Database instance
	 * @return MongoDbFactory
	 * @throws UnknownHostException
	 */
	public static MongoDbFactory mongoDbFactory(DbInstance dbInstance) throws UnknownHostException {
		if (dbInstance == null) {
			return null;
		}

		MongoClient mongoClient = mongoClient(dbInstance);
		return new SimpleMongoDbFactory(mongoClient, dbInstance.getDatabase());
	}

	/**
	 * Create default MongoConverter
	 * @param mongoDbFactory: MongoDbFactory object
	 * @return MongoConverter
	 */
	private static final MongoConverter defaultMongoConverter(MongoDbFactory mongoDbFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		CustomConversions conversions = new CustomConversions(Collections.emptyList());

		MongoMappingContext mappingContext = new MongoMappingContext();
		mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
		mappingContext.afterPropertiesSet();

		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
		converter.setCustomConversions(conversions);
		converter.afterPropertiesSet();
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}

	/**
	 * Create index
	 * @param mongoOperations: MongoOperations object.
	 * @param clazz: Collection entity class.
	 * @param indexMap: Index map, format: field, Direction
	 * @param unique: Unique index or not?
	 */
	public static <T> void createIndex(MongoOperations mongoOperations, Class<T> clazz, Map<String, Direction> indexMap, boolean unique) {
		Index index = createIndex(indexMap, unique);
		mongoOperations.indexOps(clazz).ensureIndex(index);
	}

	/**
	 * Create index
	 * @param indexMap: Index map, format: field, Direction
	 * @param unique: Unique index or not?
	 * @return Index
	 */
	private static Index createIndex(Map<String, Direction> indexMap, boolean unique) {
		Index index = new Index();

		for (Entry<String, Direction> entry : indexMap.entrySet()) {
			index.on(entry.getKey(), entry.getValue());
		}

		if (unique) {
			return index.unique();
		}

		return index;
	}

	/**
	 * Update
	 * @param mongoOperations: MongoOperations object
	 * @param id: Id value
	 * @param setField: Field need update
	 * @param setValue: Field value need update
	 * @param clazz: Collection entity class
	 * @return WriteResult
	 */
	public static WriteResult update(MongoOperations mongoOperations, String id, String setField, Object setValue, Class<?> clazz) {
		return update(mongoOperations, "_id", id, setField, setValue, clazz);
	}

	/**
	 * Update
	 * @param mongoOperations: MongoOperations object
	 * @param keyField: Primary key
	 * @param keyValue: Primary key value
	 * @param setField: Field need update
	 * @param setValue: Field value need update
	 * @param clazz: Collection entity class
	 * @return WriteResult
	 */
	public static WriteResult update(MongoOperations mongoOperations, String keyField, Object keyValue, String setField, Object setValue, Class<?> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where(keyField).is(keyValue));

		Update update = new Update();
		update.set(setField, setValue);

		return mongoOperations.updateMulti(query, update, clazz);
	}

	/**
	 * Is primary key or not?
	 * @param annotation: Field annotation object
	 * @return boolean
	 */
	public static boolean isPk(Annotation annotation) {
		return org.springframework.data.annotation.Id.class == annotation.annotationType();
	}

	/**
	 * Is field
	 * @param annotation: Field annotation object
	 * @return boolean
	 */
	public static boolean isField(Annotation annotation) {
		return org.springframework.data.mongodb.core.mapping.Field.class.equals(annotation.annotationType());
	}

	/**
	 * Get field name
	 * @param annotation: Field annotation object
	 * @return String
	 */
	public static String getFieldName(Annotation annotation) {
		org.springframework.data.mongodb.core.mapping.Field field = org.springframework.data.mongodb.core.mapping.Field.class.cast(annotation);

		if (field == null) {
			return null;
		}

		return field.value();
	}

	/**
	 * Transfer entity to Query
	 * @param entity: Entity object.
	 * @param entityFields: Entity EntityField list.
	 * @return Query
	 * @throws ReflectiveOperationException
	 */
	public static <T> Query entityToQuery(T entity, List<EntityField> entityFields) throws ReflectiveOperationException {
		if (entity == null || CollectionUtils.isNullOrEmpty(entityFields)) {
			return null;
		}

		Query query = new Query();

		for (EntityField entityField : entityFields) {
			Object value = entityField.getValue(entity);
			Annotation[] annotations = entityField.getField().getAnnotations();

			for (Annotation annotation : annotations) {
				String fieldName = null;

				if (isField(annotation)) {
					fieldName = getFieldName(annotation);
				}

				if (isPk(annotation)) {
					fieldName = "_id";
				}

				query.addCriteria(Criteria.where(fieldName).is(value));
			}
		}

		return query;
	}

	/**
	 * Transfer entity to Update
	 * @param entity: Entity object
	 * @param entityFields: Entity EntityField list
	 * @return Update
	 * @throws IllegalArgumentException
	 * @throws ReflectiveOperationException
	 */
	public static <T> Update entityToUpdate(T entity, List<EntityField> entityFields) throws IllegalArgumentException, ReflectiveOperationException {
		if (entity == null || CollectionUtils.isNullOrEmpty(entityFields)) {
			return null;
		}

		Update update = new Update();

		for (EntityField entityField : entityFields) {
			Object value = entityField.getValue(entity);
			Annotation[] annotations = entityField.getField().getAnnotations();

			for (Annotation annotation : annotations) {
				String fieldName = null;

				if (isField(annotation)) {
					fieldName = getFieldName(annotation);
				}

				if (isPk(annotation)) {
					fieldName = "_id";
				}

				if (fieldName != null) {
					update.set(fieldName, value);
				}
			}
		}

		return update;
	}

	/**
	 * Query elements by query, return a distinct list include specified field.
	 * @param mongoOperations: MongoOperations object.
	 * @param collection: Collection name.
	 * @param field: Include field.
	 * @param query: Query object.
	 * @return {@code List<T>}
	 */
	public static <T> List<T> distinct(MongoOperations mongoOperations, String collection, String field, Query query) {
		String excludeField = "_id";

		if (!excludeField.equals(field)) {
			excludeField = field;
		}

		query.fields().exclude(excludeField).include(field);

		@SuppressWarnings("unchecked")
		List<T> result = mongoOperations.getCollection(collection).distinct(field, query.getQueryObject());

		List<T> list = new LinkedList<>();
		result.iterator().forEachRemaining(list::add);
		return list;
	}
}
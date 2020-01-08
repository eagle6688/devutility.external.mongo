package devutility.external.mongo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;

import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.models.EntityField;
import devutility.internal.lang.reflect.GenericTypeUtils;
import devutility.internal.util.CollectionUtils;

/**
 * 
 * PairUtils
 * 
 * @author: Aldwin Su
 * @version: 2020-01-08 14:47:38
 */
public class PairUtils {
	/**
	 * Convert entity to Pair<Query, Update> object.
	 * @param entity MongoDB entity.
	 * @param queryEntityFields EntityField objects for query.
	 * @param updateEntityFields EntityField objects for update.
	 * @return {@code Pair<Query,Update>}
	 * @throws ReflectiveOperationException from objectToQuery or objectToUpdate.
	 */
	public static Pair<Query, Update> objectToPair(Object entity, List<EntityField> queryEntityFields, List<EntityField> updateEntityFields) throws ReflectiveOperationException {
		Query query = QueryUtils.objectToQuery(entity, queryEntityFields);
		Update update = UpdateUtils.objectToUpdate(entity, updateEntityFields);
		return Pair.of(query, update);
	}

	/**
	 * Convert objects to Pair<Query, Update> objects.
	 * @param entities MongoDB entities.
	 * @param queryEntityFields EntityField objects for query.
	 * @param updateEntityFields EntityField objects for update.
	 * @return {@code List<Pair<Query,Update>>}
	 * @throws ReflectiveOperationException from objectToPair.
	 */
	public static List<Pair<Query, Update>> objectsToPairs(Collection<?> entities, List<EntityField> queryEntityFields, List<EntityField> updateEntityFields) throws ReflectiveOperationException {
		List<Pair<Query, Update>> list = new LinkedList<>();

		for (Object entity : entities) {
			list.add(objectToPair(entity, queryEntityFields, updateEntityFields));
		}

		return list;
	}

	/**
	 * Convert objects to Pair<Query, Update> objects.
	 * @param entities MongoDB entities.
	 * @param queryFields Fields for query.
	 * @param updateFields Fields for update.
	 * @return List<Pair<Query,Update>>
	 * @throws ReflectiveOperationException from objectsToPairs.
	 */
	public static List<Pair<Query, Update>> objectsToPairs(Collection<?> entities, String[] queryFields, String[] updateFields) throws ReflectiveOperationException {
		if (CollectionUtils.isNullOrEmpty(entities)) {
			return new LinkedList<>();
		}

		Class<?> genericClass = GenericTypeUtils.getGenericClass(entities);
		List<EntityField> queryEntityFields = ClassUtils.getIncludedEntityFields(queryFields, genericClass);
		List<EntityField> updateEntityFields = null;

		if (updateFields == null || updateFields.length == 0) {
			updateEntityFields = ClassUtils.getEntityFields(genericClass);
		} else {
			updateEntityFields = ClassUtils.getIncludedEntityFields(updateFields, genericClass);
		}

		return objectsToPairs(entities, queryEntityFields, updateEntityFields);
	}

	/**
	 * Convert objects to Pair<Query, Update> objects.
	 * @param entities MongoDB entities.
	 * @param queryFields Fields for query.
	 * @return List<Pair<Query,Update>>
	 * @throws ReflectiveOperationException from objectsToPairs.
	 */
	public static List<Pair<Query, Update>> objectsToPairs(Collection<?> entities, Collection<String> queryFields) throws ReflectiveOperationException {
		if (CollectionUtils.isNullOrEmpty(entities)) {
			return new LinkedList<>();
		}

		Class<?> genericClass = GenericTypeUtils.getGenericClass(entities);
		List<EntityField> queryEntityFields = ClassUtils.getIncludedEntityFields(queryFields, genericClass);
		List<EntityField> updateEntityFields = ClassUtils.getEntityFields(genericClass);
		return objectsToPairs(entities, queryEntityFields, updateEntityFields);
	}

	/**
	 * Convert objects to Pair<Query, Update> objects.
	 * @param entities MongoDB entities.
	 * @param queryFields Fields for query.
	 * @return List<Pair<Query,Update>>
	 * @throws ReflectiveOperationException from objectsToPairs.
	 */
	public static List<Pair<Query, Update>> objectsToPairs(Collection<?> entities, String[] queryFields) throws ReflectiveOperationException {
		Set<String> queryFieldSet = new HashSet<>(Arrays.asList(queryFields));
		return objectsToPairs(entities, queryFieldSet);
	}

	/**
	 * Return Query objects from provided Pair<Query, Update> objects.
	 * @param pairs {@code Pair<Query, Update>} objects.
	 * @return List<Query>
	 */
	public static List<Query> getQueries(List<Pair<Query, Update>> pairs) {
		List<Query> list = new ArrayList<>(pairs.size());

		for (Pair<Query, Update> pair : pairs) {
			list.add(pair.getFirst());
		}

		return list;
	}
}
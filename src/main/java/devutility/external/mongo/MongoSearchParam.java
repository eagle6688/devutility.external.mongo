package devutility.external.mongo;

import java.util.Collection;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import devutility.internal.lang.StringUtils;
import devutility.internal.util.CollectionUtils;

public abstract class MongoSearchParam {
	/**
	 * Deleted field
	 */
	private Boolean deleted;

	/**
	 * Page index
	 */
	private int pageIndex;

	/**
	 * Page size
	 */
	private int pageSize;

	/**
	 * Count for limit
	 */
	private int topCount;

	/**
	 * Query
	 */
	private Query query;

	/**
	 * Constructor
	 */
	public MongoSearchParam() {
		query = new Query();
	}

	/**
	 * Get Query object.
	 * @return Query
	 */
	public Query getQuery() {
		clear();
		buildQuery();

		if (deleted != null) {
			is("Deleted", deleted);
		}

		if (topCount > 0) {
			top(topCount);
		}

		if (pageIndex > 0 && pageSize > 0) {
			paging(pageIndex, pageSize);
		}

		return query;
	}

	/**
	 * Clear query.
	 */
	public void clear() {
		query = new Query();
	}

	/**
	 * Method for build Query object for sub class.
	 */
	protected abstract void buildQuery();

	/**
	 * Is query.
	 * @param field Field in mongo
	 * @param value Search value
	 */
	protected void is(String field, Object value) {
		if (StringUtils.isNullOrEmpty(field)) {
			return;
		}

		query.addCriteria(Criteria.where(field).is(value));
	}

	/**
	 * In query.
	 * @param field Field in mongo
	 * @param values Search values
	 */
	protected void in(String field, Collection<?> values) {
		if (StringUtils.isNullOrEmpty(field) || CollectionUtils.isNullOrEmpty(values)) {
			return;
		}

		query.addCriteria(Criteria.where(field).in(values));
	}

	/**
	 * Paging query.
	 * @param pageIndex
	 * @param pageSize void
	 */
	private void paging(int pageIndex, int pageSize) {
		if (pageIndex < 1 || pageSize < 1) {
			return;
		}

		int skip = (pageIndex - 1) * pageSize;
		query.skip(skip);
		query.limit(pageSize);
	}

	/**
	 * Top query.
	 * @param topCount void
	 */
	private void top(int topCount) {
		if (topCount < 1) {
			return;
		}

		query.limit(topCount);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTopCount() {
		return topCount;
	}

	public void setTopCount(int topCount) {
		this.topCount = topCount;
	}
}
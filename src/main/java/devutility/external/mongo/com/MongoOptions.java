package devutility.external.mongo.com;

import java.util.StringJoiner;

/**
 * 
 * MongoOptions
 * 
 * @author: Aldwin Su
 * @version: 2020-01-10 14:19:05
 */
public class MongoOptions {
	/**
	 * Specifies the name of the replica set, if the mongod is a member of a replica set.
	 */
	private String replicaSet;

	/**
	 * The time in milliseconds to attempt a connection before timing out. The default is never to timeout.
	 */
	private Integer connectTimeoutMS;

	/**
	 * The time in milliseconds to attempt a send or receive on a socket before the attempt times out. The default is never
	 * to timeout.
	 */
	private Integer socketTimeoutMS;

	/**
	 * Compressors to enable network compression for communication between this client and a mongod/mongos instance.
	 */
	private MongoCompressor[] compressors;

	/**
	 * An integer that specifies the compression level if using zlib for network compression. You can specify an integer
	 * value ranging from -1 to 9, default is -1=6
	 */
	private Integer zlibCompressionLevel;

	/**
	 * The maximum number of connections in the connection pool. The default value is 100.
	 */
	private Integer maxPoolSize;

	/**
	 * The minimum number of connections in the connection pool. The default value is 0.
	 */
	private Integer minPoolSize;

	/**
	 * The maximum number of milliseconds that a connection can remain idle in the pool before being removed and closed.
	 */
	private Integer maxIdleTimeMS;

	/**
	 * A number that the driver multiples the maxPoolSize value to, to provide the maximum number of threads allowed to wait
	 * for a connection to become available from the pool.
	 */
	private Integer waitQueueMultiple;

	/**
	 * The maximum time in milliseconds that a thread can wait for a connection to become available.
	 */
	private Integer waitQueueTimeoutMS;

	/**
	 * <p>
	 * The w option requests acknowledgment that the write operation has propagated to a specified number of mongod
	 * instances or to mongod instances with specified tags.
	 * </p>
	 * <p>
	 * w: 1 Requests acknowledgment that the write operation has propagated to the standalone mongod or the primary in a
	 * replica set. w: 1 is the default write concern for MongoDB.
	 * </p>
	 * <p>
	 * w: 0 Requests no acknowledgment of the write operation. However, w: 0 may return information about socket exceptions
	 * and networking errors to the application.
	 * </p>
	 * <p>
	 * w:majority Requests acknowledgment that write operations have propagated to the calculated majority of the
	 * data-bearing voting members (i.e. primary and secondaries with members[n].votes greater than 0).
	 * </p>
	 */
	private String w;

	/**
	 * The j option requests acknowledgment from MongoDB that the write operation has been written to the on-disk journal.
	 */
	private Boolean j;

	/**
	 * This option specifies a time limit, in milliseconds, for the write concern. wtimeout is only applicable for w values
	 * greater than 1. If you do not specify the wtimeout option and the level of write concern is unachievable, the write
	 * operation will block indefinitely. Specifying a wtimeout value of 0 is equivalent to a write concern without the
	 * wtimeout option.
	 */
	private Integer wtimeout;

	/**
	 * The readConcern option allows you to control the consistency and isolation properties of the data read from replica
	 * sets and replica set shards. For more detailed information, see
	 * https://docs.mongodb.com/manual/reference/read-concern/
	 */
	private ReadConcernLevel readConcernLevel;

	public String getReplicaSet() {
		return replicaSet;
	}

	public void setReplicaSet(String replicaSet) {
		this.replicaSet = replicaSet;
	}

	public Integer getConnectTimeoutMS() {
		return connectTimeoutMS;
	}

	public void setConnectTimeoutMS(Integer connectTimeoutMS) {
		this.connectTimeoutMS = connectTimeoutMS;
	}

	public Integer getSocketTimeoutMS() {
		return socketTimeoutMS;
	}

	public void setSocketTimeoutMS(Integer socketTimeoutMS) {
		this.socketTimeoutMS = socketTimeoutMS;
	}

	public MongoCompressor[] getCompressors() {
		return compressors;
	}

	public String getCompressorsStr() {
		if (compressors == null) {
			return null;
		}

		StringJoiner joiner = new StringJoiner(",");

		for (int i = 0; i < compressors.length; i++) {
			joiner.add(compressors[i].toString());
		}

		return joiner.toString();
	}

	public void setCompressors(MongoCompressor[] compressors) {
		this.compressors = compressors;
	}

	public Integer getZlibCompressionLevel() {
		return zlibCompressionLevel;
	}

	public void setZlibCompressionLevel(Integer zlibCompressionLevel) {
		this.zlibCompressionLevel = zlibCompressionLevel;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public Integer getMaxIdleTimeMS() {
		return maxIdleTimeMS;
	}

	public void setMaxIdleTimeMS(Integer maxIdleTimeMS) {
		this.maxIdleTimeMS = maxIdleTimeMS;
	}

	public Integer getWaitQueueMultiple() {
		return waitQueueMultiple;
	}

	public void setWaitQueueMultiple(Integer waitQueueMultiple) {
		this.waitQueueMultiple = waitQueueMultiple;
	}

	public Integer getWaitQueueTimeoutMS() {
		return waitQueueTimeoutMS;
	}

	public void setWaitQueueTimeoutMS(Integer waitQueueTimeoutMS) {
		this.waitQueueTimeoutMS = waitQueueTimeoutMS;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public Boolean getJ() {
		return j;
	}

	public void setJ(Boolean j) {
		this.j = j;
	}

	public Integer getWtimeout() {
		return wtimeout;
	}

	public void setWtimeout(Integer wtimeout) {
		this.wtimeout = wtimeout;
	}

	public ReadConcernLevel getReadConcernLevel() {
		return readConcernLevel;
	}

	public void setReadConcernLevel(ReadConcernLevel readConcernLevel) {
		this.readConcernLevel = readConcernLevel;
	}
}
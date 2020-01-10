package devutility.external.mongo.model;

/**
 * 
 * MongoOptions
 * 
 * @author: Aldwin Su
 * @version: 2020-01-10 14:19:05
 */
public class MongoOptions {
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
	 * This option specifies a time limit, in milliseconds, for the write concern. wtimeout is only applicable for w values
	 * greater than 1. If you do not specify the wtimeout option and the level of write concern is unachievable, the write
	 * operation will block indefinitely. Specifying a wtimeout value of 0 is equivalent to a write concern without the
	 * wtimeout option.
	 */
	private Integer wtimeout;

	/**
	 * The j option requests acknowledgment from MongoDB that the write operation has been written to the on-disk journal.
	 */
	private Boolean j;

	/**
	 * The readConcern option allows you to control the consistency and isolation properties of the data read from replica
	 * sets and replica set shards. For more detailed information, see
	 * https://docs.mongodb.com/manual/reference/read-concern/
	 */
	private String readConcernLevel;

}
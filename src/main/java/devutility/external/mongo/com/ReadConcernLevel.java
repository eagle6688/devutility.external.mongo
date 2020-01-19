package devutility.external.mongo.com;

/**
 * 
 * ReadConcernLevel
 * 
 * @author: Aldwin Su
 * @version: 2020-01-19 13:31:21
 */
public enum ReadConcernLevel {
	/**
	 * The query returns data from the instance with no guarantee that the data has been written to a majority of the
	 * replica set members (i.e. may be rolled back).
	 */
	LOCAL,

	/**
	 * The query returns data from the instance with no guarantee that the data has been written to a majority of the
	 * replica set members (i.e. may be rolled back).
	 */
	AVAILABLE,

	/**
	 * The query returns the data that has been acknowledged by a majority of the replica set members. The documents
	 * returned by the read operation are durable, even in the event of failure.
	 */
	MAJORITY,

	/**
	 * The query returns data that reflects all successful majority-acknowledged writes that completed prior to the start of
	 * the read operation. The query may wait for concurrently executing writes to propagate to a majority of replica set
	 * members before returning results.
	 */
	LINEARIZABLE,

	/**
	 * If the transaction is not part of a causally consistent session, upon transaction commit with write concern
	 * "majority", the transaction operations are guaranteed to have read from a snapshot of majority-committed data. If the
	 * transaction is part of a causally consistent session, upon transaction commit with write concern "majority", the
	 * transaction operations are guaranteed to have read from a snapshot of majority-committed data that provides causal
	 * consistency with the operation immediately preceding the transaction start.
	 */
	SNAPSHOT;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
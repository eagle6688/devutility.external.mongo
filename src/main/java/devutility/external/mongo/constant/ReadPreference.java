package devutility.external.mongo.constant;

/**
 * 
 * ReadPreference
 * 
 * @author: Aldwin Su
 * @version: 2020-01-19 14:33:56
 */
public enum ReadPreference {
	/**
	 * Default mode. All operations read from the current replica set primary.
	 */
	PRIMARY("primary"),

	/**
	 * In most situations, operations read from the primary member of the set. However, if the primary is unavailable, as is
	 * the case during failover situations, operations read from secondary members that satisfy the read preference’s
	 * maxStalenessSeconds and tag sets.
	 */
	PRIMARYPREFERRED("primaryPreferred"),

	/**
	 * Operations read only from the secondary members of the set. If no secondaries are available, then this read operation
	 * produces an error or exception.
	 */
	SECONDARY("secondary"),

	/**
	 * In most situations, operations read from secondary members, but in situations where the set consists of a single
	 * primary (and no other members), the read operation will use the replica set’s primary.
	 */
	SECONDARYPREFERRED("secondaryPreferred"),

	/**
	 * The driver reads from a member whose network latency falls within the acceptable latency window. Reads in the nearest
	 * mode do not consider whether a member is a primary or secondary when routing read operations: primaries and
	 * secondaries are treated equivalently. The read preference member selection documentation describes the process in
	 * detail.
	 */
	NEAREST("nearest");

	private String value;

	private ReadPreference(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
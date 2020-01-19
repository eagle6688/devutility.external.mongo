package devutility.external.mongo.com;

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
	PRIMARY;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
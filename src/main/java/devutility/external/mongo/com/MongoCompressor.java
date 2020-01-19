package devutility.external.mongo.com;

/**
 * 
 * MongoCompressors
 * 
 * @author: Aldwin Su
 * @version: 2020-01-19 11:02:18
 */
public enum MongoCompressor {
	/**
	 * snappy
	 */
	SNAPPY,

	/**
	 * Available in MongoDB 3.6 or greater
	 */
	ZLIB,

	/**
	 * Available in MongoDB 4.2 or greater
	 */
	ZSTD;

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
package devutility.external.mongo.mongodbutils;

import org.springframework.data.mongodb.core.MongoTemplate;

import devutility.external.mongo.MongoDbUtils;
import devutility.internal.test.BaseTest;
import devutility.internal.test.TestExecutor;

public class MongoTemplateTest extends BaseTest {
	@Override
	public void run() {
		@SuppressWarnings("deprecation")
		MongoTemplate mongoTemplate = MongoDbUtils.mongoTemplate("dbconfig.properties", "mongodb");

		if (mongoTemplate == null) {
			println("null");
		}
	}

	public static void main(String[] args) {
		TestExecutor.concurrentRun(100, MongoTemplateTest.class, (data) -> {
			System.out.println(data);
		});
	}
}
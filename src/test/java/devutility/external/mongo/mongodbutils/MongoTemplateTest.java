package devutility.external.mongo.mongodbutils;

import java.util.List;

import com.devutility.test.entity.mongo.Student;

import devutility.external.mongo.BaseTest;
import devutility.internal.test.TestExecutor;

public class MongoTemplateTest extends BaseTest {
	@Override
	public void run() {
		List<Student> list = Student.list(1);
		mongoOperations.save(list.get(0));
	}

	public static void main(String[] args) {
		TestExecutor.run(MongoTemplateTest.class);
	}
}
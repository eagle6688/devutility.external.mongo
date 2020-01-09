package devutility.external.mongo.internal;

import java.util.List;

import devutility.external.mongo.BaseTestForMongo;
import devutility.external.mongo.entity.Student;
import devutility.internal.test.TestExecutor;

public class SaveTest extends BaseTestForMongo {
	@Override
	public void run() {
		List<Student> list = Student.list(1);
		mongoOperations.save(list.get(0));
	}

	public static void main(String[] args) {
		TestExecutor.run(SaveTest.class);
	}
}
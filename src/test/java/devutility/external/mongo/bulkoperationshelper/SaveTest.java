package devutility.external.mongo.bulkoperationshelper;

import java.util.Arrays;
import java.util.List;

import com.mongodb.bulk.BulkWriteResult;

import devutility.external.mongo.BaseTest;
import devutility.external.mongo.entity.Student;
import devutility.external.mongo.helper.MongoBulkHelper;
import devutility.internal.test.TestExecutor;

public class SaveTest extends BaseTest {
	@Override
	public void run() {
		List<Student> list = Student.list(100);
		MongoBulkHelper bulkOperationsHelper = new MongoBulkHelper(mongoOperations);

		try {
			BulkWriteResult result = bulkOperationsHelper.save(list, Arrays.asList("id"), Student.class);
			System.out.println(result);
		} catch (IllegalArgumentException | ReflectiveOperationException e) {
			e.printStackTrace();
		}

		try {
			BulkWriteResult result = bulkOperationsHelper.save(list, Student.class);
			System.out.println(result);
		} catch (IllegalArgumentException | ReflectiveOperationException e) {
			e.printStackTrace();
		}

		println("Ok");
	}

	public static void main(String[] args) {
		TestExecutor.run(SaveTest.class);
	}
}
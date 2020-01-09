package devutility.external.mongo.bulkhelper;

import java.util.List;

import devutility.external.mongo.BaseTestForMongo;
import devutility.external.mongo.MongoBulkHelper;
import devutility.external.mongo.entity.Student;
import devutility.internal.test.TestExecutor;

public class UpsertTest extends BaseTestForMongo {
	@Override
	public void run() {
		List<Student> list = Student.list(10);
		String[] queryFields = { "id" };
		String[] updateFields = { "id", "name", "age" };
		MongoBulkHelper mongoBulkHelper = new MongoBulkHelper(mongoOperations);

		try {
			mongoBulkHelper.upsert(list, queryFields, updateFields);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}

		list = Student.list(20);

		try {
			mongoBulkHelper.upsert(list, queryFields);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(UpsertTest.class);
	}
}
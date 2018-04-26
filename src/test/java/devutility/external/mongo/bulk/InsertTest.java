package devutility.external.mongo.bulk;

import java.util.List;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.devutility.test.model.Student;
import com.mongodb.bulk.BulkWriteResult;

import devutility.external.mongo.BaseTest;
import devutility.internal.test.TestExecutor;

public class InsertTest extends BaseTest {
	@Override
	public void run() {
		List<Student> list = Student.list(100);
		BulkOperations bulkOperations = mongoOperations.bulkOps(BulkMode.UNORDERED, "Student");

		for (Student item : list) {
			Query query = new Query();
			query.addCriteria(Criteria.where("Number").is(item.getNumber()));

			Update update = new Update();
			update.set("_id", item.getNumber());
			update.set("Number", item.getNumber());
			update.set("Name", item.getName());
			update.set("Age", item.getAge());
			update.set("Birthday", item.getBirthday());

			bulkOperations.upsert(query, update);
		}

		BulkWriteResult bulkWriteResult = bulkOperations.execute();
		println(bulkWriteResult.getInsertedCount());
		println(bulkWriteResult.getModifiedCount());
		println(bulkWriteResult.getMatchedCount());
		println(bulkWriteResult.getUpserts().size());
		System.out.println(bulkWriteResult);
	}

	public static void main(String[] args) {
		TestExecutor.run(InsertTest.class);
	}
}
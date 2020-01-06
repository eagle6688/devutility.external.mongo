package devutility.external.mongo.mongoutils;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import devutility.external.mongo.BaseTest;
import devutility.external.mongo.MongoUtils;
import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.models.EntityField;
import devutility.internal.test.TestExecutor;
import devutility.internal.test.data.model.Student;

public class ObjectToQueryTest extends BaseTest {
	@Override
	public void run() {
		List<EntityField> entityFields = ClassUtils.getEntityFields(Student.class);
		Student model = new Student();
		model.setName("Student-1");

		try {
			Query query = MongoUtils.objectToQuery(model, null);
			println(query.toString());

			query = MongoUtils.objectToQuery(model, entityFields, false);
			println(query.toString());

			query = MongoUtils.objectToQueryByFields(model, Arrays.asList("name"));
			println(query.toString());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(ObjectToQueryTest.class);
	}
}
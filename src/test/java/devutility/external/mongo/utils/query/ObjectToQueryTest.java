package devutility.external.mongo.utils.query;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import devutility.external.mongo.BaseTestForMongo;
import devutility.external.mongo.utils.QueryUtils;
import devutility.internal.lang.ClassUtils;
import devutility.internal.model.EntityField;
import devutility.internal.test.TestExecutor;
import devutility.test.model.Student;

public class ObjectToQueryTest extends BaseTestForMongo {
	@Override
	public void run() {
		List<EntityField> entityFields = ClassUtils.getEntityFields(Student.class);
		Student model = new Student();
		model.setName("Student-1");

		try {
			Query query = QueryUtils.objectToQuery(model, null);
			println(query.toString());

			query = QueryUtils.objectToQuery(model, entityFields, false);
			println(query.toString());

			query = QueryUtils.objectToQueryByFields(model, Arrays.asList("name"));
			println(query.toString());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(ObjectToQueryTest.class);
	}
}
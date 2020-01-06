package devutility.external.mongo.mongoutils;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import devutility.external.mongo.BaseTest;
import devutility.external.mongo.MongoUtils;
import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.models.EntityField;
import devutility.internal.test.TestExecutor;
import devutility.internal.test.data.model.Student;

public class ObjectToUpdateTest extends BaseTest {
	@Override
	public void run() {
		List<EntityField> entityFields = ClassUtils.getEntityFields(Student.class);
		Student model = new Student();
		model.setName("Student-1");

		try {
			Update update = MongoUtils.objectToUpdate(model, null);
			println(update.toString());

			update = MongoUtils.objectToUpdate(model, entityFields, false);
			println(update.toString());
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(ObjectToUpdateTest.class);
	}
}
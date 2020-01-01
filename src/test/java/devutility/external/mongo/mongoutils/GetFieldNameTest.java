package devutility.external.mongo.mongoutils;

import java.util.List;

import devutility.external.mongo.BaseTest;
import devutility.external.mongo.MongoUtils;
import devutility.external.mongo.entity.Student;
import devutility.internal.lang.ClassUtils;
import devutility.internal.lang.models.EntityField;
import devutility.internal.test.TestExecutor;

public class GetFieldNameTest extends BaseTest {
	@Override
	public void run() {
		List<EntityField> entityFields = ClassUtils.getEntityFields(Student.class);

		for (EntityField entityField : entityFields) {
			println(MongoUtils.getFieldName(entityField.getField()));
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(GetFieldNameTest.class);
	}
}
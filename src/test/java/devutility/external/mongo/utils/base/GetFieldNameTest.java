package devutility.external.mongo.utils.base;

import java.util.List;

import devutility.external.mongo.BaseTestForMongo;
import devutility.external.mongo.entity.Student;
import devutility.external.mongo.utils.BaseMongoUtils;
import devutility.internal.lang.ClassUtils;
import devutility.internal.model.ObjectField;
import devutility.internal.test.TestExecutor;

public class GetFieldNameTest extends BaseTestForMongo {
	@Override
	public void run() {
		List<ObjectField> entityFields = ClassUtils.getEntityFields(Student.class);

		for (ObjectField entityField : entityFields) {
			println(BaseMongoUtils.getFieldName(entityField.getField()));
		}
	}

	public static void main(String[] args) {
		TestExecutor.run(GetFieldNameTest.class);
	}
}
package devutility.external.mongo.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Student")
public class Student {
	@Id
	private int id;

	@Field("Name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<Student> list(int count) {
		List<Student> list = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			int number = i + 1;

			Student student = new Student();
			student.setId(number);
			student.setName(String.format("Student_%d", number));
			list.add(student);
		}

		return list;
	}
}
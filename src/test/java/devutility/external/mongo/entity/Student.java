package devutility.external.mongo.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import devutility.internal.util.RandomUtils;

@Document(collection = "Student")
public class Student {
	@Id
	private int id;

	@Field("Name")
	private String name;

	@Field
	private int gender;

	private int age;

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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static List<Student> list(int count) {
		List<Student> list = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			int number = i + 1;

			Student student = new Student();
			student.setId(number);
			student.setName(String.format("Student_%d", number));
			student.setAge(RandomUtils.getNumber(15, 21));
			list.add(student);
		}

		return list;
	}
}
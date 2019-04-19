package devutility.external.mongo.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class User {
	@Id
	private String mID;
	private int id;
	private String name;
	private LocalDate birthday;

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

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

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
}
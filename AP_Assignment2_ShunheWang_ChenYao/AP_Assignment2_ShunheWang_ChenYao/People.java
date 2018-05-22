package model;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

public class People {
	private String userName;
	private String status;
	private String sex;
	private int age;
	private String state;
	private String imgPath;

	public People(String userName, String status, String sex, int age, String state) {
		this.userName = userName;
		this.status = status;
		this.sex = sex;
		this.age = age;
		this.state = state;
	}

	public People(String userName, String status, String sex, int age, String state, String imgPath) {
		this(userName, status, sex, age, state);
		this.imgPath = imgPath;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}

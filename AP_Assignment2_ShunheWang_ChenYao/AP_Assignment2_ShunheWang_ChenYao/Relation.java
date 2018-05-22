package model;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

public class Relation {
	private String firstName;
	private String secondName;
	private String relationship;

	public Relation(String firstName, String secondName, String relationship) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.relationship = relationship;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
}

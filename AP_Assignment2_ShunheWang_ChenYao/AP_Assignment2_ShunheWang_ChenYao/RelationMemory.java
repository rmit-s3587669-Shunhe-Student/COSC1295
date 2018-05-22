package model;
/**
 * @author Shunhe Wang s3587669
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import controller.dataInteraction;

public class RelationMemory {
	private ArrayList<Relation> peopleRelation;
	private String relationFilePath;

	public RelationMemory() {
		peopleRelation = new ArrayList<Relation>();
		relationFilePath = "relation.txt";
		try {
			read();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Delete the relation obj int txt file or database
	public void delete(String name, String relation) {
		for (Iterator<Relation> it = peopleRelation.iterator(); it.hasNext();) {
			Relation rObj = (Relation) it.next();
			if (rObj.getFirstName().equals(name) && rObj.getRelationship().equals(relation)) {
				it.remove();
			}
			if (rObj.getSecondName().equals(name) && rObj.getRelationship().equals(relation)) {
				it.remove();
			}
		}
		try {
			write();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Add new relation obj
	public void add(String firstName, String secondName, String relation) {
		Relation people = new Relation(firstName, secondName, relation);
		peopleRelation.add(people);
		try {
			write();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Read relation file
	public void read() throws IOException {
		this.peopleRelation = importData();
		if (peopleRelation == null) {
			System.out.println("It is empty.");
		}
	}

	public ArrayList<Relation> importData() throws IOException {
		ArrayList<Relation> temp = new ArrayList<Relation>();
		dataInteraction peopleData = new dataInteraction();
		ArrayList<String> str = peopleData.readFile(relationFilePath);

		if (str != null) {
			for (int i = 0; i < str.size(); i++) {
				String oneLineInfo = str.get(i);
				// Segment the String
				String[] sourceStrArray = oneLineInfo.split(",");
				String firstName = sourceStrArray[0];
				String secondName = sourceStrArray[1];
				String relationship = sourceStrArray[2];

				Relation rObj = new Relation(firstName, secondName, relationship);
				temp.add(rObj);
			}
		}
		return temp;
	}

	// Write data into txt file
	public void write() throws IOException {
		dataInteraction peopleData = new dataInteraction();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < peopleRelation.size(); i++) {
			String firstName = peopleRelation.get(i).getFirstName();
			String secondStatus = peopleRelation.get(i).getSecondName();
			String relationship = peopleRelation.get(i).getRelationship();
			String str = firstName + "," + secondStatus + "," + relationship;
			temp.add(str);
		}
		peopleData.writeFile(relationFilePath, temp);
	}

	public ArrayList<Relation> getPeopleRelation() {
		return peopleRelation;
	}

	public void setPeopleRelation(ArrayList<Relation> peopleRelation) {
		this.peopleRelation = peopleRelation;
	}

}

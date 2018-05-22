package controller;
/**
 * @author Shunhe Wang s3587669
 */

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import model.People;
import model.PeopleMemory;
import model.Relation;
import model.RelationMemory;

public class mainControl extends JFrame {

	private static final long serialVersionUID = 1L;
	private RelationMemory rm;
	private PeopleMemory pm;

	public mainControl() {
		// Initialize PeopleMemory and RelationMemory obj.
		pm = new PeopleMemory();
		rm = new RelationMemory();
	}

	// Get rm obj
	public RelationMemory getRelationMemory() {
		return rm;
	}

	// Get pm obj
	public PeopleMemory getPeopleMemory() {
		return pm;
	}

	// Add new user
	public void addNewUser(String userName, String status, String sex, int age, String state, String imgPath) {
		pm.add(userName, status, sex, age, state, imgPath);
	}

	// Find direct relation between two people
	public Vector<String> searchDirectRelation(String firstName, String secondName) {
		Vector<String> temp = new Vector<String>();
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if ((rel.getFirstName().equals(firstName) && rel.getSecondName().equals(secondName))
					|| (rel.getFirstName().equals(secondName) && rel.getSecondName().equals(firstName))) {
				temp.add(rel.getRelationship());
			}
		}
		if (temp.size() > 0) {
			return temp;
		} else {
			return null;
		}
	}

	// Add new relation between two people
	public void addRelation(String firstName, String secondName, String relation) {
		if (firstName.compareTo(secondName) > 0) {
			rm.add(secondName, firstName, relation);
		} else {
			rm.add(firstName, secondName, relation);
		}
	}

	// Get name's gender
	public String getGender(String name) {
		String gender = "";
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People p = pm.getPeopleProfile().get(i);
			String str = p.getUserName();
			if (name.equalsIgnoreCase(str)) {
				gender = p.getSex();
			}
		}
		return gender;
	}

	// check relation, including parent relation, couple relation, classmate
	// relation, colleague relation, friend relation between two people
	public boolean verifyRelation(String name1, String name2, String relation) {
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(name1) && rel.getSecondName().equals(name2)
					&& rel.getRelationship().equals(relation)) {
				return true;
			}
			if (rel.getFirstName().equals(name2) && rel.getSecondName().equals(name1)
					&& rel.getRelationship().equals(relation)) {
				return true;
			}
		}
		return false;
	}

	// check this people is single
	public boolean isSingle(String name) {
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(name) && rel.getRelationship().equals("Couple")) {
				return false;
			}
			if (rel.getSecondName().equals(name) && rel.getRelationship().equals("Couple")) {
				return false;
			}
		}
		return true;
	}

	// get people age
	public int getAge(String name) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People p = pm.getPeopleProfile().get(i);
			String str = p.getUserName();
			if (name.equalsIgnoreCase(str)) {
				return p.getAge();
			}
		}
		return -1;
	}

	// check age so that we know this people is adult/ child/ young child
	public String checkAge(String name) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People p = pm.getPeopleProfile().get(i);
			String str = p.getUserName();
			if (name.equalsIgnoreCase(str)) {
				int age = p.getAge();
				if (age < 3) {
					return "yc"; // young child
				} else if (age < 17) {
					return "c"; // child
				} else {
					return "a"; // adult
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param jlist
	 * @param vec
	 * @overload
	 */
	public void loadData(JList<String> jlist, Vector<String> vec) {
		ListModel<String> jListModel = new DefaultComboBoxModel<String>(vec); // establish default model
		jlist.setModel(jListModel);
	}

	/**
	 * 
	 * @param jlist
	 * @param results
	 * @overload
	 */
	public void loadData(JList<String> jlist, ArrayList<String> results) {
		Vector<String> nameList = new Vector<String>();
		for (int i = 0; i < results.size(); i++) {
			String temp = results.get(i);
			nameList.add(temp);
		}
		if (nameList.size() > 0) {
			ListModel<String> jListModel = new DefaultComboBoxModel<String>(nameList); // establish default model
			jlist.setModel(jListModel);
			jlist.setPreferredSize(new Dimension(192, 173));
		} else {
			String[] noData = new String[1];
			noData[0] = "No data";
			ListModel jListModel = new DefaultComboBoxModel(noData); // establish default model
			jlist.setModel(jListModel);
		}
	}

	/**
	 * 
	 * @param jlist
	 * @overload
	 */
	public void loadData(JList<String> jlist) {
		Vector<String> nameList = new Vector<String>();
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			String tempName = pm.getPeopleProfile().get(i).getUserName();
			nameList.add(tempName);
		}
		jlist.setPreferredSize(new Dimension(50, 50));
		if (nameList.size() > 0) {
			ListModel jListModel = new DefaultComboBoxModel(nameList); // Set model
			jlist.setModel(jListModel);
			jlist.setPreferredSize(new Dimension(100, 700));
			// jlist.setPreferredSize(new Dimension(50, 50));
		} else {
			String[] noData = new String[1];
			noData[0] = "No data";
			ListModel<String> jListModel = new DefaultComboBoxModel<String>(noData); // Set model
			jlist.setModel(jListModel);
		}
	}

	/**
	 * 
	 * @param jlist
	 * @param name
	 * @param relation
	 * @overload
	 */
	public void loadData(JList<String> jlist, String name, String relation) {
		Vector<String> childList = new Vector<String>();
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(name) && rel.getRelationship().equals(relation)) {
				childList.add(rel.getSecondName());
			}
			if (rel.getSecondName().equals(name) && rel.getRelationship().equals(relation)) {
				childList.add(rel.getFirstName());
			}
		}
		if (childList.size() > 0) {
			ListModel jListModel = new DefaultComboBoxModel(childList); // Set model
			jlist.setModel(jListModel);
			jlist.setPreferredSize(new Dimension(192, 173));
			// jlist.setListData(noData);
		} else {
			String[] noData = new String[1];
			noData[0] = "No data";
			ListModel<String> jListModel = new DefaultComboBoxModel<String>(noData); // Set model
			jlist.setModel(jListModel);
			jlist.setPreferredSize(new Dimension(192, 173));
		}
	}

	// If people list has this name, return this name. Else, return "-".
	public String existName(String str) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			String tempName = pm.getPeopleProfile().get(i).getUserName();
			if (tempName.equalsIgnoreCase(str)) {
				return tempName;
			}
		}
		return "-";
	}

	// check gender
	public String checkGender(String name) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			String tempName = pm.getPeopleProfile().get(i).getUserName();
			String gender = pm.getPeopleProfile().get(i).getSex();
			if (tempName.equalsIgnoreCase(name)) {
				if (gender.equalsIgnoreCase("M")) {
					return "M";
				}
				if (gender.equalsIgnoreCase("F")) {
					return "F";
				}
			}
		}
		return "-";
	}

	/**
	 * Get parent name
	 * 
	 * @param name
	 * @param relation
	 * @return
	 * @overload
	 */
	public ArrayList<String> getParentName(String name, String relation) {
		ArrayList<String> searchResult = new ArrayList<String>();
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(name) && rel.getRelationship().equals(relation)) {
				searchResult.add(rel.getSecondName());
			}
			if (rel.getSecondName().equals(name) && rel.getRelationship().equals(relation)) {
				searchResult.add(rel.getFirstName());
			}
		}
		if (!searchResult.isEmpty()) {
			return searchResult;
		}
		return null;
	}

	/**
	 *
	 * @param parent
	 * @return
	 * @overload
	 */
	public String getParentName(String parent) {
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(parent) && rel.getRelationship().equals("Couple")) {
				return rel.getFirstName() + "," + rel.getSecondName();
			}
			if (rel.getSecondName().equals(parent) && rel.getRelationship().equals("Couple")) {
				return rel.getFirstName() + "," + rel.getSecondName();
			}
		}
		return "-";
	}

	// Add relation data in jtable
	public void loadDataForRelation(JTable table) {
		String[] columnHeader = { "Name", "Name", "Relation" };
		String[][] data = new String[rm.getPeopleRelation().size()][3];
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			data[i][0] = rel.getFirstName();
			data[i][1] = rel.getSecondName();
			data[i][2] = rel.getRelationship();
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeader);
		table.setModel(defaultTableModel);
		table.setPreferredSize(new Dimension(192, 700));
	}

	/**
	 * 
	 * @param table
	 * @overload
	 */
	public void loadData(JTable table) {
		String[] columnHeader = { "Name", "Status", "Gender", "Age", "State" };
		String[][] data = new String[pm.getPeopleProfile().size()][5];
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People p = pm.getPeopleProfile().get(i);
			data[i][0] = p.getUserName();
			data[i][1] = p.getStatus();
			data[i][2] = p.getSex();
			data[i][3] = String.valueOf(p.getAge());
			data[i][4] = p.getState();
		}
		DefaultTableModel defaultTableModel = new DefaultTableModel(data, columnHeader);
		table.setModel(defaultTableModel);
		table.setPreferredSize(new Dimension(192, 700));
	}

	// Get people obj
	public People getPersonDetail(String str) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People p = pm.getPeopleProfile().get(i);
			String name = p.getUserName();
			if (name.equalsIgnoreCase(str)) {
				return p;
			}
		}
		return null;
	}

	// Remove people obj
	public void removePeople(String name) {
		for (int i = 0; i < pm.getPeopleProfile().size(); i++) {
			People pObj = pm.getPeopleProfile().get(i);
			if (pObj.getUserName().equals(name)) {
				pm.delete(pObj);
			}
		}
	}

	// Verify the relation
	public boolean verifiedRelation(String name, String relation) {
		for (int i = 0; i < rm.getPeopleRelation().size(); i++) {
			Relation rel = rm.getPeopleRelation().get(i);
			if (rel.getFirstName().equals(name) && rel.getRelationship().equals(relation)) {
				return true;
			}
			if (rel.getSecondName().equals(name) && rel.getRelationship().equals(relation)) {
				return true;
			}
		}
		return false;
	}

	// Remove relation
	public void removeRelation(String name, String relation) {
		rm.delete(name, relation);
	}
}

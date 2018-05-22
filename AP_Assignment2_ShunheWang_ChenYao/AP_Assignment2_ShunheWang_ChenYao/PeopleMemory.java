package model;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

import java.io.IOException;
import java.util.ArrayList;
import controller.dataInteraction;

public class PeopleMemory {
	private ArrayList<People> peopleProfile;
	private String peopleFilePath;
	
	public PeopleMemory() {
		//initialize new people arraylist and store data from txt file or database
		this.peopleProfile=new ArrayList<People>();
		peopleFilePath="people.txt";
		try {
			read(); 	//Read data
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	//Add new people obj
	public void add(String userName, String status, String sex, int age, String state, String imgPath) {
		People people=new People(userName, status, sex, age, state, imgPath);
		peopleProfile.add(people);
		try {
			write();   //Directly write into txt file or database
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	//Delete people obj
	public void delete(People obj) {
		peopleProfile.remove(obj);
		try {
			write();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	//Read data
	public void read() throws IOException {
		this.peopleProfile=importData();
		if(peopleProfile==null) {
			System.out.println("It is empty.");
		}
	}
	
	//Read data
	protected ArrayList<People> importData() throws IOException {
		ArrayList<People> temp= new ArrayList<People>();
		dataInteraction peopleData=new dataInteraction();
		ArrayList<String> str= peopleData.readFile(peopleFilePath);

		if(str!=null) {
			for(int i=0; i<str.size();i++) {
				String oneLineInfo=str.get(i);
				String[] sourceStrArray= oneLineInfo.split(",");			//Segment String and use Array to store every a small piece of String
				String name= sourceStrArray[0];
				String status=sourceStrArray[1];
				String sex=sourceStrArray[2];
				int age=Integer.parseInt(sourceStrArray[3]);
				String state=sourceStrArray[4];
				String imgPath=sourceStrArray[5];
				People pObj=new People(name, status, sex, age, state, imgPath);
				temp.add(pObj);
			}
		}
		return temp;
	}
	
	//Write data into txt file
	public void write() throws IOException {
		dataInteraction peopleData=new dataInteraction();
		ArrayList<String> temp=new ArrayList<String>();
		for(int i=0;i<peopleProfile.size();i++) {
			String name=peopleProfile.get(i).getUserName();
			String status=peopleProfile.get(i).getStatus();
			String sex=peopleProfile.get(i).getSex();
			int age=peopleProfile.get(i).getAge();
			String state=peopleProfile.get(i).getState();
			String imgPath=peopleProfile.get(i).getImgPath();
			if(imgPath.equals("")) {
				imgPath="\"\"";
			}
			String str=name+","+status+","+sex+","+age+","+state+","+imgPath;
			temp.add(str);
		}
		peopleData.writeFile(peopleFilePath, temp);
	}
	
	//Get people arraylist
	public ArrayList<People> getPeopleProfile() {
		return peopleProfile;
	}
	
	//Set people arraylist
	public void setPeopleProfile(ArrayList<People> peopleProfile) {
		this.peopleProfile = peopleProfile;
	}
	
}

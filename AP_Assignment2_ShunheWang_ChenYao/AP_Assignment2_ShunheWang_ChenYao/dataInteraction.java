package controller;
/**
 * @author Shunhe s3587669
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import tools.Message;

public class dataInteraction {

	public dataInteraction() {
	}

	// Read file
	public ArrayList<String> readFile(String fName) throws IOException {
		String directoryName = "database";
		String fileName = directoryName + "\\" + fName;
		File directory = new File(String.valueOf(directoryName));
		File fileNameInDatabase = new File(String.valueOf(fileName));
		if (!directory.exists()) { 										// If folder cannot find, return null
			Message.showMessage("Folder cannot find");
			return null;
		}
		ArrayList<String> temp = new ArrayList<String>();
		if (!fileNameInDatabase.exists()) { 					// If txt file cannot find, connect with database.
			Message.showMessage("File cannot find");
			if (readFromDataBase(temp)) {
				Message.showMessage("Database connection successfully");
				return temp;
			} else { // If database cannot find, return null
				Message.showMessage("No found txt file and database");
				return null;
			}
		}

		// write file
		FileReader fw = new FileReader(fileName);
		BufferedReader bw = new BufferedReader(fw);
		String thisLine = null;
		while ((thisLine = bw.readLine()) != null) {
			temp.add(thisLine);
		}
		bw.close();
		return temp;
	}

	public boolean readFromDataBase(ArrayList<String> temp) {
		try {
			// Connection with SQLite's JDBC
			Class.forName("org.sqlite.JDBC");

			// Establish people.db
			Connection conn = DriverManager.getConnection("jdbc:sqlite:database/people.db");

			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists people;"); // Drop all possible tables in database
			// Create new people table
			stat.executeUpdate(
					"create table people(name varchar(20), status varchar(20),gender char(1),age int,state varchar(20),photopath varchar(20));");// 创建一个表，两列
			// Store one data in there
			stat.executeUpdate("insert into people values('shunhe','rmit','M',45,'VIC','\"\"');");
			// Search data in people table
			ResultSet rs = stat.executeQuery("select * from people;");

			while (rs.next()) { // find all data to store into arraylist
				String str = rs.getString("name") + "," + rs.getString("status") + "," + rs.getString("gender") + ","
						+ rs.getInt("age") + "," + rs.getString("state") + "," + rs.getString("photopath");
				temp.add(str);
			}
			rs.close();
			conn.close(); // close the database connection

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	// write the file
	public void writeFile(String fName, ArrayList<String> peopleData) throws IOException {
		String directoryName = "database";
		String fileName = directoryName + "\\" + fName;
		File directory = new File(String.valueOf(directoryName));
		File fileNameInDatabase = new File(String.valueOf(fileName));
		if (!directory.exists()) { // if folder cannot find, create new folder
			directory.mkdir();
		}
		if (!fileNameInDatabase.exists()) { // If txt file cannot find, create new file to store data
			fileNameInDatabase.createNewFile();
		}
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < peopleData.size(); i++) {
			bw.write(peopleData.get(i));
			bw.newLine();
		}
		bw.close();
	}

}

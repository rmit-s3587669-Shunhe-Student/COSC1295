package view;
/**
 * 
 * @author Chen Yao s3373565
 *
 */
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import controller.mainControl;
import myException.EmptyInputException;
import myException.InvalidationInputException;
import myException.NoAvailableException;
import myException.NoParentException;
import myException.NoSuchAgeException;
import myException.SameNameException;
import tools.InterfaceVerification;
import tools.Message;

public class AddPeopleInterface extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	//Define mainControl obj
	mainControl mc;
	//Define requirement element
	JLabel  jl1, jl2, jl3, jl4, jl5, jl6, jl7;										//name, status,gender,age,state,imgPath,parentname
	JButton jb1, jb2;																//Add/Cancel button
	JRadioButton jrb3_1, jrb3_2;
	ButtonGroup bg;
	JTextField jtf1, jtf2, jtf4, jtf6, jtf7;
	JComboBox<String> jcb5;
	JPanel jp1, jp2, jp3, jp4;
	
	public AddPeopleInterface(mainControl mc) {
		super();
		this.mc=mc;
		//Initialize elements
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jl1=new JLabel("Name");
		jl2=new JLabel("Status");
		jl3=new JLabel("Gender");
		jl4=new JLabel("Age");
		jl5=new JLabel("State");
		jl6=new JLabel("Photo Path");
		jl7=new JLabel("Parent Name");
		
		jtf1=new JTextField(10);
		jtf2=new JTextField(10);
		jtf4=new JTextField(5);
		jtf6=new JTextField(10);
		jtf7=new JTextField(10);
		
		jb1=new JButton("Add");
		jb1.addActionListener(this);						//add actionlistener for 'Add' button
		jb2=new JButton("Cancel");
		jb2.addActionListener(this);						//add actionlistener for 'Cancel' button
		
		jrb3_1=new JRadioButton("M");
		jrb3_2=new JRadioButton("F");
		bg=new ButtonGroup();								//Make 'gender' radio button
		bg.add(jrb3_1);
		bg.add(jrb3_2);
		
		String[] jg= {"-","ACT","NSW","NT","QLD","SA","TAS","WA"};					//initialize 'State' Arrays
		jcb5=new JComboBox<String>(jg);																//Make 'State' ComboBox
		
		//Set this jdialog layout
		this.setLayout(new GridLayout(4,1));
		//add elements into right jpanel
		jp1.add(jl1);
		jp1.add(jtf1);
		jp1.add(jl2);
		jp1.add(jtf2);
		
		jp2.add(jl3);
		jp2.add(jrb3_1);
		jp2.add(jrb3_2);
		jp2.add(jl4);
		jp2.add(jtf4);
		jp2.add(jl5);
		jp2.add(jcb5);
		
		jp3.add(jl6);
		jp3.add(jtf6);
		jp3.add(jl7);
		jp3.add(jtf7);
		
		jp4.add(jb1);
		jp4.add(jb2);
		
		//Add jpanels above into jdialog
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		
		//Design the jdialog size and location
		int width=650;
		int height=450;
		this.setSize(width, height);
		Toolkit tk = this.getToolkit();
		Dimension dm=tk.getScreenSize();
		//Jdiglog locates at the mid of computer screen
		this.setLocation((int)(dm.getWidth()-width)/2, (int)(dm.getHeight()-height)/2);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==jb1) {									//Choose 'Add' button
			//Define the local variables to store the data from interface
			String name="";
			String status="";
			String age="";
			String state="";
			String gender="";
			String imgPath="";
			String momName="";
			String dadName="";
			try {
				name=jtf1.getText().trim();							//eliminate possible spaces when input on interface
				if(!InterfaceVerification.isEmpty(name)) {   //check name textbox is empty
					Message.showMessage("Please input name");
					throw new EmptyInputException("Empty input");
				}else if(!InterfaceVerification.isString(name)) {	//check name input is String only
					Message.showMessage("Invalid input, Please input string only");
					throw new InvalidationInputException("Invalidation input: only input String");
				}else if(!this.mc.existName(name).equals("-")){		//check name has existed in txt file
					Message.showMessage("This person has existed already ");
					throw new SameNameException("Database has existed this data");
				}
			
			//Get the content of status textbox
			status=jtf2.getText().trim();								//eliminate possible spaces when input on interface
			if(!InterfaceVerification.isEmpty(status)) {		//check input empty
				Message.showMessage("Please input status");
				throw new EmptyInputException("Empty input");
			}else if(!InterfaceVerification.isString(status)) {	//check status is String only
				Message.showMessage("Invalid input, Please input string only");
				throw new InvalidationInputException("Invalidation input: only input String");
			}
			
			//Get gender choice
			if(jrb3_1.isSelected()) {
				gender=jrb3_1.getText();
			}
			if(jrb3_2.isSelected()) {
				gender=jrb3_2.getText();
			}
			if(!InterfaceVerification.isEmpty(gender)) {			//check gender is choice or not
				Message.showMessage("Please choose gender");
				throw new EmptyInputException("Empty input");
			}
			
			//Get age
			age=jtf4.getText();
			if(!InterfaceVerification.isEmpty(age)) {				//check age is empty or not
				Message.showMessage("Please input age");
				throw new EmptyInputException("Empty input");
			}else if(!InterfaceVerification.isNumeric(age)) {	//check age is only number
				Message.showMessage("Invalidation input: only input Integer");
				throw new InvalidationInputException("Not integer");
			}else if(!InterfaceVerification.inAgeRange(age)) {	//check age is in adaptable range
				Message.showMessage("Invalidation input: age range is 0-150");
				throw new NoSuchAgeException("Out of age range");
			}
			
			//Get state 
			state=(String) jcb5.getSelectedItem();
			if(state.equals("-")) {													//check state is choice or not
				Message.showMessage("Please select state");
				throw new EmptyInputException("Empty input");
			}
			
			//Get parent
			String parent=jtf7.getText();
			int minAdultAge=17;
			if(Integer.parseInt(age)>=minAdultAge) {				//If new person is adult, this person does not need to input parent data
				if(!parent.equals("")) {
					Message.showMessage("Adult does not need to add parent");
					parent="";
					throw new NoParentException("Adults cannot input parent info");
				}
			}else {																				//If new person is not adult, is child or young child
				if(!InterfaceVerification.isEmpty(parent)) {			//check parentName is empty or not
					Message.showMessage("Please input parent name");
					throw new NoParentException("Empty input");
				}else if(!InterfaceVerification.isString(parent)) {	//check parentName is Only String
					Message.showMessage("Invalidation input: only input String");
					throw new InvalidationInputException("Only string");
				}else {
					if(mc.existName(parent).equals("-")) {			//Check parentName is in txt file
						Message.showMessage("Invalidation input: no relative record in data");
						throw new NoParentException("No found in database");
					}else {
						if(mc.getParentName(parent).equals("-")) {		//check parentName has married or single or child or young child
							Message.showMessage("Invalidation input: child and single person cannot become parent");
							throw new NoParentException("Single Adult");
						}else {																			//If parentName's obj has married, return this couple name.
							String temp=mc.getParentName(parent);
							String tempArray[]=temp.split(",");
							momName=tempArray[0];
							dadName=tempArray[1];
						}
					}
				}			
			}
			
			//Get photo path
			imgPath=jtf6.getText();
			String regex=".*(.jpg|.png|.gif)$";
			Pattern p = Pattern.compile(regex);
			if(!imgPath.equals("")) {
				Matcher m=p.matcher(imgPath);
				if(!m.matches()) {
					Message.showMessage("Please input right photo format(.jpg/jng/.gif) or input empty");
					throw new NoAvailableException("Wrong img format");
				}
				imgPath="photo//"+imgPath;
			}
			
			
			
			//Add new person
			this.mc.addNewUser(name, status, gender, Integer.parseInt(age), state, imgPath);
			if(!momName.equals("")&&!dadName.equals("")) {	//has parent name, add new 'Parent' relation in relation.txt file
				mc.addRelation(name, momName, "Parent");
				mc.addRelation(name, dadName, "Parent");
			}
			Message.showMessage("Add successfully");
			this.dispose();
			} catch (EmptyInputException | InvalidationInputException | SameNameException | NoSuchAgeException | NoParentException | NoAvailableException e) {
				System.out.println(e);
			}
			}else if(arg0.getSource()==jb2) {			//If click 'Cancel' button, close add new user interface.
			this.dispose();
		}	
	
	}
}

package view;
/**
 * @author Shunhe Wang s3587669
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import controller.mainControl;
import myException.EmptyInputException;
import myException.NoAvailableException;
import myException.NotToBeClassmatesException;
import myException.NotToBeColleaguesException;
import myException.NotToBeCoupledException;
import myException.NotToBeFriendException;
import myException.SameNameException;
import myException.TooYoungException;
import tools.Message;


public class AddRelationInterface extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private mainControl mc;
	// Set requirment elements
	private JPanel jp1, jp2, jp3, jp4, jp_top, jp_down;
	private JLabel jl1_name, jl2_relAttribute, jl3_name, jl4_allRelation;
	public JList<String> jlist1, jlist3;
	private JScrollPane jscrol_list1, jscrol_list3, JScrollPane_jtab;
	private JComboBox<String> jcb2;
	private final String[] relationAttr = { "-", "Couple", "Friend", "Classmate", "Colleague" };
	public JTable jtb;
	private JButton jb_add;
	private JSplitPane jsp_Relation = null;

	public AddRelationInterface(mainControl mc) {
		this.mc = mc;

		// Initialize all elements
		jl1_name = new JLabel("Name");
		jl2_relAttribute = new JLabel("Relation Attribute");
		jl3_name = new JLabel("Name");
		jl4_allRelation = new JLabel("Existed Relationship");

		jlist1 = new JList<String>();
		this.mc.loadData(jlist1); // load data in jlist
		jscrol_list1 = new JScrollPane(); // create jsp obj and then put jlist into jscrollpane
		jscrol_list1.setViewportView(jlist1);

		jlist3 = new JList<String>(); // load data in another jlist
		this.mc.loadData(jlist3); // create jsp obj and then put jlist into jscrollpane
		jscrol_list3 = new JScrollPane(jlist3);
		jcb2 = new JComboBox<String>(relationAttr);
		jcb2.setSize(162, 162);

		jtb = new JTable(); // create new jtable to show all relations
		this.mc.loadDataForRelation(jtb);
		JScrollPane_jtab = new JScrollPane(jtb);
		jb_add = new JButton("Add Relation");
		jb_add.addMouseListener(this);

		// set jpanels layout
		jp1 = new JPanel(new BorderLayout());
		jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 40));
		jp3 = new JPanel(new BorderLayout());
		jp4 = new JPanel(new BorderLayout());

		jp_top = new JPanel(new GridLayout(1, 4));
		jp_down = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 40));
		jp_down.setLayout(new FlowLayout(FlowLayout.RIGHT));

		// add elements into jpanels
		jp1.add(jl1_name, "North");
		jp1.add(jscrol_list1, "Center");

		jp2.add(jl2_relAttribute, "North");
		jp2.add(jcb2, "Center");

		jp3.add(jl3_name, "North");
		jp3.add(jscrol_list3, "Center");

		jp4.add(jl4_allRelation, "North");
		jp4.add(JScrollPane_jtab, "Center");

		// add jpanels 1,2,3,4 into jp_top
		jp_top.add(jp1);
		jp_top.add(jp2);
		jp_top.add(jp3);
		jp_top.add(jp4);

		// add jbutton into jp_down
		jp_down.add(jb_add);

		// jp_top, jp_down is put into jsp_Relation
		jsp_Relation = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jp_top, jp_down); // vertical split
		// set jp_top is allocated how many pixels
		jsp_Relation.setDividerLocation(350);
		// set border line is zero
		jsp_Relation.setDividerSize(0);

		// set jpanel layout
		this.setLayout(new BorderLayout());
		this.add(jsp_Relation, "Center");
		// set size and location at computer screen
		int width = 650;
		int height = 450;
		this.setSize(width, height);
		Toolkit tk = this.getToolkit();
		Dimension dm = tk.getScreenSize();
		this.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == jb_add) { // If choose 'Add' button
			String firstChoiceName = "";
			String secondChoiceName = "";
			String choiceRelationship = "";
			try {
				if (jlist1.getSelectedIndex() < 0) { // check someone has choice name or not on first jlist
					Message.showMessage("Please choice name in first name list");
					throw new EmptyInputException("No choice name");
				}
				choiceRelationship = jcb2.getSelectedItem().toString();
				if (choiceRelationship.equals("-")) { // check someone has choice which relationship
					Message.showMessage("Please choice relationship");
					throw new EmptyInputException("No choice relationship");
				}
				if (jlist3.getSelectedIndex() < 0) { // check someone has choice name or not on second jlist
					Message.showMessage("Please choice name in second name list");
					throw new EmptyInputException("No choice name");
				}

				firstChoiceName = jlist1.getModel().getElementAt(jlist1.getSelectedIndex()).toString(); // Get data from
																										// first jlist
				secondChoiceName = jlist3.getModel().getElementAt(jlist3.getSelectedIndex()).toString(); // Get data
																											// from
																											// second
																											// jlist
				if (firstChoiceName.equals(secondChoiceName)) { // verify two choice name is not same
					Message.showMessage("Please two different name");
					throw new SameNameException("Choice same name");
				}
				// Add new "Couple" relationship
				if (choiceRelationship.equals("Couple")) {
					this.toCheckCouple(firstChoiceName, secondChoiceName);
				}

				// Add new "Friend" relationship
				if (choiceRelationship.equals("Friend")) {
					this.toCheckFriend(firstChoiceName, secondChoiceName);
				}

				// Add new "Classmate" relationship
				if (choiceRelationship.equals("Classmate")) {
					this.toCheckClassmate(firstChoiceName, secondChoiceName);
				}

				// Add new "Colleague" relationship
				if (choiceRelationship.equals("Colleague")) {
					this.toCheckColleague(firstChoiceName, secondChoiceName);
				}

				// Add new relationship
				mc.addRelation(firstChoiceName, secondChoiceName, choiceRelationship);
				// load relation data on jtable again
				mc.loadDataForRelation(jtb);
				Message.showMessage("Add relation successfully");
			} catch (EmptyInputException | SameNameException | NotToBeCoupledException | NoAvailableException
					| TooYoungException | NotToBeFriendException | NotToBeClassmatesException
					| NotToBeColleaguesException exc) {
				System.out.println(exc);
			}
		}
	}

	// If choose colleague relation
	public void toCheckColleague(String firstName, String secondName) throws NotToBeColleaguesException {
		if (mc.checkAge(firstName).equals("yc")) { // If firstName is young child, it is impossible to have colleagues
													// relation
			Message.showMessage("FirstName is young child, cannot become colleagues");
			throw new NotToBeColleaguesException("Young child");
		}

		if (mc.checkAge(secondName).equals("yc")) { // If secondName is young child, it is impossible to have colleagues
													// relation
			Message.showMessage("SecondName is young child, cannot become colleagues");
			throw new NotToBeColleaguesException("Young child");
		}

		if (mc.checkAge(firstName).equals("c")) { // If firstName is child, it is impossible to have colleagues relation
			Message.showMessage("FirstName is child, cannot become colleagues");
			throw new NotToBeColleaguesException("Child");
		}

		if (mc.checkAge(secondName).equals("c")) { // If secondName is child, it is impossible to have colleagues
													// relation
			Message.showMessage("SecondName is child, cannot become colleagues");
			throw new NotToBeColleaguesException("Child");
		}

		if (mc.verifyRelation(firstName, secondName, "Colleague")) { // Only two adults, it is possible to have
																		// colleague relation
			Message.showMessage("They has already colleagues"); // check two adults has colleague relation, if yes,
																// cannot add
			throw new NotToBeColleaguesException("has already colleague relation");
		}

	}

	// If choice relation is classmate
	public void toCheckClassmate(String firstName, String secondName) throws NotToBeClassmatesException {
		if (mc.checkAge(firstName).equals("yc")) { // If firstName is young child, it is impossible to have classmate
													// relation
			Message.showMessage("FirstName is young child, cannot become classmate");
			throw new NotToBeClassmatesException("Young child");
		}

		if (mc.checkAge(secondName).equals("yc")) { // If secondName is young child, it is impossible to have classmate
													// relation
			Message.showMessage("SecondName is young child, cannot become classmate");
			throw new NotToBeClassmatesException("Young child");
		}

		if ((mc.checkAge(firstName).equals("c") && mc.checkAge(secondName).equals("a")) // One adult, One child, cannot
																						// have
				|| (mc.checkAge(firstName).equals("a") && mc.checkAge(secondName).equals("c"))) { // classmate relation
			Message.showMessage("One adult cannot make classmate relationship with one child");
			throw new NotToBeClassmatesException("One adult, one child");
		}

		if (mc.verifyRelation(firstName, secondName, "Classmate")) { // Finally, check two adults or two child has
																		// existed classmate
			Message.showMessage("They has already classmate"); // relation
			throw new NotToBeClassmatesException("has already classmate relation");
		}
	}

	// If choice relation is friend
	public void toCheckFriend(String firstName, String secondName) throws TooYoungException, NotToBeFriendException {
		if (mc.checkAge(firstName).equals("yc")) { // If firstName is young child, it is impossible to have friend
													// relation
			Message.showMessage("FirstName is young child, young child cannot make friend");
			throw new TooYoungException("Young child");
		}

		if (mc.checkAge(secondName).equals("yc")) { // If secondName is young child, it is impossible to have friend
													// relation
			Message.showMessage("SecondName is young child, young child cannot make friend");
			throw new TooYoungException("Young child");
		}

		if ((mc.checkAge(firstName).equals("c") && mc.checkAge(secondName).equals("a")) // One adult, one child cannot
																						// have friend relation
				|| (mc.checkAge(firstName).equals("a") && mc.checkAge(secondName).equals("c"))) {
			Message.showMessage("A adult cannot make friend with a child");
			throw new NotToBeFriendException("One adult and One child");
		}

		if (mc.checkAge(firstName).equals("a") && mc.checkAge(secondName).equals("a")) { // Two adults or two child has
																							// had friend relation
			if (mc.verifyRelation(firstName, secondName, "Friend")) {
				Message.showMessage("They has already friend");
				throw new NotToBeFriendException("has friendship bewteen them");
			}

			if (mc.verifyRelation(firstName, secondName, "Couple")) { // Two adults are couple so that cannot become
																		// friend.
				Message.showMessage("They has already couple, Cannot become friend");
				throw new NotToBeFriendException("has already couple");
			}
		}

		// When two child
		if (mc.checkAge(firstName).equals("c") && mc.checkAge(secondName).equals("c")) {
			if (mc.verifyRelation(firstName, secondName, "Friend")) { // check two children have had friend relation
				Message.showMessage("They has already friend");
				throw new NotToBeFriendException("has friendship bewteen them");
			}

			// Get parent obj
			ArrayList<String> firstObjParent = mc.getParentName(firstName, "Parent");
			String firstObjAdu1 = firstObjParent.get(0).toString();
			String firstObjAdu2 = firstObjParent.get(1).toString();
			ArrayList<String> secondObjParent = mc.getParentName(secondName, "Parent");
			String secondObjAdu1 = secondObjParent.get(0).toString();
			String secondObjAdu2 = secondObjParent.get(1).toString();
			if ((firstObjAdu1.equals(secondObjAdu1) && firstObjAdu2.equals(secondObjAdu2))
					|| (firstObjAdu1.equals(secondObjAdu2) && firstObjAdu2.equals(secondObjAdu1))) { // check two child
																										// has same
																										// parent
				Message.showMessage("They are family, cannot become friend");
				throw new NotToBeFriendException("Same parent");
			}

			// Get two child age
			int firstNameAge = mc.getAge(firstName);
			int secondNameAge = mc.getAge(secondName);
			int maxDifAge = 3;
			if (Math.abs(firstNameAge - secondNameAge) > maxDifAge) { // The difference age is over 3, cannot become
																		// friend
				Message.showMessage("The difference age is over 3 years, cannot become friend");
				throw new NotToBeFriendException("OutOfDifferentAgeRange");
			}

		}
	}

	// If new choice relation is couple
	public void toCheckCouple(String firstName, String secondName)
			throws NotToBeCoupledException, NoAvailableException {
		if (mc.checkAge(firstName).equals("c") || mc.checkAge(firstName).equals("yc")) { // young child or child must
																							// have not couple
																							// relationship
			Message.showMessage("FirstName is not adult, only adult has possible couple relationship");
			throw new NotToBeCoupledException("First name is not adult");
		}

		if (mc.checkAge(secondName).equals("c") || mc.checkAge(secondName).equals("yc")) { // young child or child must
																							// have not couple
																							// relationship
			Message.showMessage("SecondName is not adult, only adult has possible couple relationship");
			throw new NotToBeCoupledException("Second name is not adult");
		}

		// Only two adults are possible to have couple relationship.
		if (mc.checkGender(firstName).equals(mc.checkGender(secondName))) { // Check two adult has same gender
			Message.showMessage("Two persons is same as gender, Cannot become couple relationship");
			throw new NoAvailableException("Same gender");
		}

		if (mc.verifyRelation(firstName, secondName, "Couple")) { // check two adults has couple relationship with each
																	// other
			Message.showMessage("Two persons has already couple with each other");
			throw new NoAvailableException("has been couple");
		}

		if (mc.verifyRelation(firstName, secondName, "Friend")) { // check if these two adults have already friend, they
																	// will not become friend
			Message.showMessage("They has already friend, Cannot become couple");
			throw new NotToBeCoupledException("has already been friend");
		}

		if (!mc.isSingle(firstName) || !mc.isSingle(secondName)) { // check two adult is single at the same time.
																	// Because we only check they has couple
																	// relationship with each other. Maybe it is
																	// possible to have one of two has couple relation
																	// with others
			Message.showMessage("At least one person has already been couple with others");
			throw new NoAvailableException("No two single adults");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

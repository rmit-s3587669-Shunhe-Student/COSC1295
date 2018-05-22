package view;
/**
 * @author Shunhe
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.mainControl;
import myException.ExistedParentRelationException;
import tools.Message;

public class DisplayManInterface extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	// Define requirement elements
	private JPanel p1, p2, p3, p4;
	private JLabel p3_Lab1;
	private JButton p4_jb1, p4_jb2, p4_jb3, p4_jb4;
	public JTable jtable;
	private JScrollPane jsp;

	private final int minChildAge = 3;
	private final int minAdultAge = 17;

	mainControl mc;

	public DisplayManInterface(mainControl mc) {
		this.mc = mc;
		// intialize elements
		p1 = new JPanel(new BorderLayout());
		jtable = new JTable();
		jtable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.mc.loadData(jtable);
		jtable.setPreferredSize(new Dimension(100, 600));
		jsp = new JScrollPane(jtable);
		p1.add(jsp);
		// intialize jp3's elements
		p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3_Lab1 = new JLabel("Note: 'Repaint' button shows the lastest people information");
		p3.add(p3_Lab1);

		// intialize jp4's elements
		p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p4_jb1 = new JButton("Repaint");
		p4_jb1.addActionListener(this);
		p4_jb2 = new JButton("Add");
		p4_jb2.addActionListener(this);
		p4_jb3 = new JButton("Delete");
		p4_jb3.addActionListener(this);
		p4_jb4 = new JButton("Search");
		p4_jb4.addActionListener(this);
		p4.add(p4_jb1);
		p4.add(p4_jb2);
		p4.add(p4_jb3);
		p4.add(p4_jb4);

		// put jp3,jp4 into jp2
		p2 = new JPanel(new BorderLayout());
		p2.add(p3, "West");
		p2.add(p4, "East");

		this.setLayout(new BorderLayout());
		this.add(p1);
		this.add(p2, "South");
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.p4_jb2) { // enter add people interface
			new AddPeopleInterface(mc);
		}
		if (e.getSource() == this.p4_jb1) { // click 'repaint' button to repaint jtable if update some info
			mc.loadData(jtable);
		}

		if (e.getSource() == this.p4_jb3) { // handle when to click 'delete' button
			if (this.jtable.getSelectedRow() < 0) // no choice in jtb
			{
				Message.showMessage("Please choose one query object ");
			} else if (this.jtable.getValueAt(this.jtable.getSelectedRow(), 0).equals("")) { // cannot choose empty
																								// space in jtb
				Message.showMessage("You cannot choose empty space in people list");
			} else {
				String str = (String) this.jtable.getValueAt(this.jtable.getSelectedRow(), 0);
				int strAge = Integer.parseInt((String) this.jtable.getValueAt(this.jtable.getSelectedRow(), 3));
				if (strAge < minChildAge) { // if choose young child, need delete parent relation at the same time.
											// Other relation is not.
					mc.removeRelation(str, "Parent");
					mc.removePeople(str);
					Message.showMessage("Delete successfully");
				} else if (strAge < minAdultAge) { // chooce child, need to delete parent relation and friend relation
													// if it existed in file.
					mc.removeRelation(str, "Parent");
					mc.removeRelation(str, "Friend");
					mc.removeRelation(str, "Classmate");
					mc.removePeople(str);
					Message.showMessage("Delete successfully");
				} else { // if choose adult
					try {
						if (mc.verifiedRelation(str, "Couple")) { // If this person has couple relationship
							if (mc.verifiedRelation(str, "Parent")) { // if choose adult with child, cannot delete. And
																		// delete his/her children firstly.
								Message.showMessage(str + " has children, you should remove children first");
								throw new ExistedParentRelationException("Has children");
							}
							mc.removeRelation(str, "Couple");
						}
						mc.removeRelation(str, "Classmate");
						mc.removeRelation(str, "Colleague");
						mc.removeRelation(str, "Friend");
						mc.removePeople(str);
						Message.showMessage("Delete successfully");

					} catch (ExistedParentRelationException e1) {
						System.out.println(e);
					}

				}
			}
		}

		if (e.getSource() == this.p4_jb4) { // show someone detail info
			if (this.jtable.getSelectedRow() < 0) // check click or not
			{
				Message.showMessage("Please choose one query object ");
			} else if (this.jtable.getValueAt(this.jtable.getSelectedRow(), 0).equals("")) { // check choice empty space
				Message.showMessage("You cannot choose empty space in people list");
			} else {
				// Get name of clicked line on jtb and then sent this 'name' to other interface
				// to show this person's detail info
				String str = (String) this.jtable.getValueAt(this.jtable.getSelectedRow(), 0);
				new DetailInfoInterface(str, mc);
			}
		}
	}
}

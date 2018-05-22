package view;
/**
 * @author Shunhe Wang s3587669
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import controller.DFSrelationSearch;
import controller.mainControl;
import myException.EmptyInputException;
import myException.SameNameException;
import tools.Message;

public class SearchRelationInterface extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	// Define elements
	private JButton jb_directRelation, jb_indirectRelation;
	private JLabel jl_firstName, jl_secondName, jl_choice, jl_directRelation, jl_indirectRelation;
	private JList<String> jlist_firstName, jlist_secondName, jlist_directRelation, jlist_indirectRelation;
	private JPanel jp_top, jp_down, jp_firstNameList, jp_secondNameList, jp_choiceRelation, jp_directRelation,
			jp_indirectRelation;
	private JScrollPane jsrollp_jlistFirstName, jsrollp_jlistSecondName;
	private JSplitPane jsp = null;
	private mainControl mc;

	public SearchRelationInterface(mainControl mc) {
		this.mc = mc;
		jl_firstName = new JLabel("Name");
		this.jl_firstName.setFont(new Font("dialog", 1, 20));
		jl_secondName = new JLabel("Name");
		this.jl_secondName.setFont(new Font("dialog", 1, 20));
		jl_directRelation = new JLabel("Direct Relation");
		this.jl_directRelation.setFont(new Font("dialog", 1, 20));
		jl_indirectRelation = new JLabel("Indirect Relation");
		this.jl_indirectRelation.setFont(new Font("dialog", 1, 20));
		jl_choice = new JLabel("Choice relation");
		this.jl_choice.setFont(new Font("dialog", 1, 20));
		jb_directRelation = new JButton("Search Direct Relation");
		jb_directRelation.addMouseListener(this);
		jb_indirectRelation = new JButton("Search Indirect Relation");
		jb_indirectRelation.addMouseListener(this);
		jlist_firstName = new JList<String>();
		this.mc.loadData(jlist_firstName);
		jsrollp_jlistFirstName = new JScrollPane();
		jsrollp_jlistFirstName.setViewportView(jlist_firstName);
		jlist_secondName = new JList<String>();
		this.mc.loadData(jlist_secondName);
		jsrollp_jlistSecondName = new JScrollPane();
		jsrollp_jlistSecondName.setViewportView(jlist_secondName);
		jlist_directRelation = new JList<String>();
		jlist_indirectRelation = new JList<String>();

		jp_top = new JPanel(new GridLayout(1, 3));
		jp_down = new JPanel(new GridLayout(1, 2));
		jp_firstNameList = new JPanel(new BorderLayout());
		jp_secondNameList = new JPanel(new BorderLayout());
		JPanel jp_storeChoiceButton = new JPanel();
		jp_choiceRelation = new JPanel(new BorderLayout());
		jp_directRelation = new JPanel(new BorderLayout());
		jp_indirectRelation = new JPanel(new BorderLayout());

		jp_firstNameList.add(jl_firstName, "North");
		jp_firstNameList.add(jsrollp_jlistFirstName, "Center");

		jp_secondNameList.add(jl_secondName, "North");
		jp_secondNameList.add(jsrollp_jlistSecondName, "Center");

		jp_storeChoiceButton.add(jb_directRelation);
		jp_storeChoiceButton.add(jb_indirectRelation);
		jp_choiceRelation.add(jl_choice, "North");
		jp_choiceRelation.add(jp_storeChoiceButton, "Center");

		jp_directRelation.add(jl_directRelation, "North");
		jp_directRelation.add(jlist_directRelation, "Center");

		jp_indirectRelation.add(jl_indirectRelation, "North");
		jp_indirectRelation.add(jlist_indirectRelation, "Center");

		// put all jpanels into jp_top or jp_down
		jp_top.add(jp_firstNameList);
		jp_top.add(jp_secondNameList);
		jp_top.add(jp_choiceRelation);

		jp_down.add(jp_directRelation);
		jp_down.add(jp_indirectRelation);

		jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, jp_top, jp_down);
		jsp.setDividerLocation(350);
		jsp.setDividerSize(0);

		this.setLayout(new BorderLayout());
		this.add(jsp, "Center");
		int width = 650;
		int height = 450;
		Toolkit tk = this.getToolkit();
		Dimension dm = tk.getScreenSize();
		this.setLocation((int) (dm.getWidth() - width) / 2, (int) (dm.getHeight() - height) / 2);
		this.setVisible(true);
	}

	public JList<String> getJlistFirstName() {
		return jlist_firstName;
	}

	public JList<String> getJlistSecondName() {
		return jlist_secondName;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == jb_directRelation) { // show direct relation
			try {
				if (jlist_firstName.getSelectedIndex() < 0) {
					Message.showMessage("Please choice name in first name list");
					throw new EmptyInputException("No choice name");
				}

				if (jlist_secondName.getSelectedIndex() < 0) {
					Message.showMessage("Please choice name in second name list");
					throw new EmptyInputException("No choice name");
				}

				String firstChoiceName = jlist_firstName.getModel().getElementAt(jlist_firstName.getSelectedIndex())
						.toString(); // Get choice info from jlist1
				String secondChoiceName = jlist_secondName.getModel().getElementAt(jlist_secondName.getSelectedIndex())
						.toString();// Get choice info from jlist2
				if (firstChoiceName.equals(secondChoiceName)) { // cannot choose same name
					Message.showMessage("You cannot choose same name");
					throw new SameNameException("Same name");
				}

				if (mc.searchDirectRelation(firstChoiceName, secondChoiceName) == null) { // if direct relation is
																							// empty, show no data
					Vector<String> vec = new Vector<String>(); // maybe two people has more than one relation so as to
																// use vector
					vec.add("No relative info"); // vector obj can directly put jlist
					mc.loadData(jlist_directRelation, vec);
				} else { // else show direct relation
					Vector<String> vec = mc.searchDirectRelation(firstChoiceName, secondChoiceName);
					mc.loadData(jlist_directRelation, vec);
				}

			} catch (EmptyInputException | SameNameException e) {
				System.out.println(e);
			}
		}

		if (arg0.getSource() == jb_indirectRelation) { // when choose indirect relation
			try {
				if (jlist_firstName.getSelectedIndex() < 0) { // check no choice in jlist1
					Message.showMessage("Please choice name in first name list");
					throw new EmptyInputException("No choice name");
				}

				if (jlist_secondName.getSelectedIndex() < 0) { // check no choice in jlist2
					Message.showMessage("Please choice name in second name list");
					throw new EmptyInputException("No choice name");
				}

				String firstChoiceName = jlist_firstName.getModel().getElementAt(jlist_firstName.getSelectedIndex())
						.toString(); // Get name from jlist1
				String secondChoiceName = jlist_secondName.getModel().getElementAt(jlist_secondName.getSelectedIndex())
						.toString();// Get name from jlist3
				if (firstChoiceName.equals(secondChoiceName)) { // check same name
					Message.showMessage("You cannot choose same name");
					throw new SameNameException("Same name");
				}

				// use deep first search to find out all path between two nodes
				DFSrelationSearch dfs = new DFSrelationSearch();
				ArrayList<String> result = dfs.search(firstChoiceName, secondChoiceName,
						mc.getPeopleMemory().getPeopleProfile(), mc.getRelationMemory().getPeopleRelation());
				// sort for all paths got through compare how many string after String split by
				// '-->'. Then user swap to change location
				for (int i = 0; i < result.size(); i++) {
					for (int j = i; j < result.size(); j++) {
						if (result.get(i).split("-->").length > result.get(j).split("-->").length) {
							Collections.swap(result, i, j);
						}
					}
				}
				// Add 'shortest path' words for shortest path. Because the quantity of shortest
				// path may more than 1. so we need to compare every path length after split
				// with shortest length
				if (result.size() > 0) {
					int shortest = result.get(0).split("-->").length;
					for (int i = 0; i < result.size(); i++) {
						if (result.get(i).split("-->").length > shortest) {
							break;
						} else {
							result.set(i, result.get(i) + " shortest Path");
						}
					}
				}
				mc.loadData(jlist_indirectRelation, result);

			} catch (EmptyInputException | SameNameException e) {
				System.out.println(e);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

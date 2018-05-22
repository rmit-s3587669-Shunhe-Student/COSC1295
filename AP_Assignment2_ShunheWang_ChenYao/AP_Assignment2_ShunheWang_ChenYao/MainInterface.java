package view;
/**
 * @author Shunhe Wang s3587669
 */
import javax.imageio.ImageIO;
/**
 * Main interface
 * @author Shunhe Wang
 *
 */
import javax.swing.*;
import controller.mainControl;
import tools.ImagePanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainInterface extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private mainControl mc;
	public AddRelationInterface ari;
	public SearchRelationInterface sri;
	public DisplayManInterface dmi;
	private JPanel p1, p3, p5;
	private Image titleIcon, timeGg;
	private JLabel p1_lab1, p1_lab2, p1_lab3, p1_lab4, p1_lab5;
	private ImagePanel p1_imgPanel;
	private JLabel timeNow;
	private javax.swing.Timer t;
	private JSplitPane jsp1 = null;
	// Define cardLayout to complete click which label, show which relative
	// interface
	CardLayout card_p3 = new CardLayout();

	// handle middle jpanel
	public void initCenterPanels() {

		p1 = new JPanel(new BorderLayout());
		Image p1_imgPanel = null;
		try {
			p1_imgPanel = ImageIO.read(new File("toolsImg//timeBg.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// intialize hand cursor
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);
		this.p1_imgPanel = new ImagePanel(p1_imgPanel);
		this.p1_imgPanel.setLayout(new GridLayout(5, 1));
		ImageIcon im_logo = new ImageIcon("toolsImg//MiniNetLogo.jpg");
		this.p1_lab1 = new JLabel(im_logo);
		this.p1_imgPanel.add(p1_lab1);

		ImageIcon im_Display = new ImageIcon("toolsImg//display.jpg");
		im_Display.setImage(im_Display.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		this.p1_lab2 = new JLabel("Display All Data", im_Display, 0);
		this.p1_lab2.setFont(new Font("dialog", 1, 15));
		this.p1_lab2.setCursor(myCursor);
		this.p1_lab2.setEnabled(false);
		this.p1_lab2.addMouseListener(this);
		this.p1_imgPanel.add(p1_lab2);

		ImageIcon im_select = new ImageIcon("toolsImg//select.jpg");
		im_select.setImage(im_select.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		this.p1_lab3 = new JLabel("Add a relation", im_select, 0);
		this.p1_lab3.setFont(new Font("dialog", 1, 15));

		this.p1_lab3.setCursor(myCursor);
		this.p1_lab3.setEnabled(false);
		this.p1_lab3.addMouseListener(this);
		this.p1_imgPanel.add(p1_lab3);

		ImageIcon im_relation = new ImageIcon("toolsImg//relation.jpg");
		im_relation.setImage(im_relation.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		this.p1_lab4 = new JLabel("Search a Relation", im_relation, 0);
		this.p1_lab4.setFont(new Font("dialog", 1, 15));

		this.p1_lab4.setCursor(myCursor);

		this.p1_lab4.setEnabled(false);
		this.p1_lab4.addMouseListener(this);
		this.p1_imgPanel.add(p1_lab4);

		ImageIcon im_exit = new ImageIcon("toolsImg//exit.jpg");
		im_exit.setImage(im_exit.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		this.p1_lab5 = new JLabel("Exit Application", im_exit, 0);
		this.p1_lab5.setFont(new Font("dialog", 1, 15));
		// when mouse on lab, change to the shape of the hand cursor
		this.p1_lab5.setCursor(myCursor);
		this.p1_imgPanel.add(p1_lab5);
		this.p1_lab5.setEnabled(false);
		this.p1_lab5.addMouseListener(this);
		p1.add(this.p1_imgPanel);
		p3 = new JPanel(card_p3);

		dmi = new DisplayManInterface(mc);
		p3.add(dmi, "0");
		ari = new AddRelationInterface(mc);
		p3.add(ari, "1");

		sri = new SearchRelationInterface(mc);
		p3.add(sri, "2");
		jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, p1, p3);
		jsp1.setDividerLocation(350);
		jsp1.setDividerSize(0);
	}

	public MainInterface() {
		mc = new mainControl();
		try {
			titleIcon = ImageIO.read(new File("toolsImg//titleImg.png"));
		} catch (IOException e) {
			System.out.println("Img cannot be found");
			e.printStackTrace();
		}

		// handle jpanel in the mid of window
		this.initCenterPanels();

		// handle p5
		p5 = new JPanel(new BorderLayout());
		t = new Timer(1000, this);
		t.start();

		timeNow = new JLabel(" Now time: " + Calendar.getInstance().getTime().toLocaleString());

		try {
			timeGg = ImageIO.read(new File("toolsImg//MainBg.jpg"));
		} catch (IOException e) {
			System.out.println("image cannot found");
			e.printStackTrace();
		}
		ImagePanel ip = new ImagePanel(timeGg);
		ip.setLayout(new BorderLayout());
		ip.add(timeNow, "East");
		p5.add(ip);

		Container ct = this.getContentPane();
		ct.add(p5, "South");
		ct.add(jsp1, "Center");

		this.setLocation(200, 150);
		// cannot change size
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set window icon
		this.setIconImage(titleIcon);
		this.setTitle("MiniNetApplication");
		this.setSize(1050, 700);
		this.setVisible(true);
	}


	public void showinfo() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.timeNow.setText(" Now time: " + Calendar.getInstance().getTime().toLocaleString());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// show what interface through click which label. If person update some info in
		// one interface. when click other interface, other interface need also update
		// information.
		// Thus, we only re-upload jlist and jtb to show the latest information
		if (arg0.getSource() == this.p1_lab2) {
			mc.loadData(dmi.jtable);
			this.card_p3.show(p3, "0");
		} else if (arg0.getSource() == this.p1_lab3) {
			mc.loadData(ari.jlist1);
			mc.loadData(ari.jlist3);
			mc.loadDataForRelation(ari.jtb);
			this.card_p3.show(p3, "1");
		} else if (arg0.getSource() == this.p1_lab4) {
			mc.loadData(sri.getJlistFirstName());
			mc.loadData(sri.getJlistSecondName());
			this.card_p3.show(p3, "2");
		}else if(arg0.getSource() == this.p1_lab5) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// mouse on one label, change light
		if (arg0.getSource() == this.p1_lab2) {
			this.p1_lab2.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab3) {
			this.p1_lab3.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab4) {
			this.p1_lab4.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab5) {
			this.p1_lab5.setEnabled(true);
		}

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// when mouse leave, lab change gray
		if (arg0.getSource() == this.p1_lab2) {
			this.p1_lab2.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab3) {
			this.p1_lab3.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab4) {
			this.p1_lab4.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab5) {
			this.p1_lab5.setEnabled(false);
		}
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

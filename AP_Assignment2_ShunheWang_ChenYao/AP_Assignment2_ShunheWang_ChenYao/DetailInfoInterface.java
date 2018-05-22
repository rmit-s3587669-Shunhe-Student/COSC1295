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
import model.People;
import tools.CheckPhoto;

public class DetailInfoInterface extends JDialog implements MouseListener{

	private static final long serialVersionUID = 1L;
	//Define requirement elements
	private JPanel jp1, jp2, jp3, jp4, jp5, jp6, jp7, jp8;
	private JLabel jl2_title, jl2_name, jl2_status, jl2_gender, jl2_age, jl2_state, jl3_photo, jl3_img;
	private JLabel jl5_parentName, jl5_mom, jl5_dad, jl6_childName;
	private JButton jb6_close;
	private JList<String> jlist1;
	private JScrollPane jsp;
	private JSplitPane jsp1=null;
	private JSplitPane jsp2=null;
	private String name;
	private String status;
	private String gender;
	private String age;
	private String state;
	private String imgPath;
	private String mom;
	private String dad;
	private final int minAdultAge=17;
	//Define mainControl obj, because it has people data and relation data
	mainControl mc;
	
	
	//handle string data sent from DisplayManInterface
	public void getPeopleInfoChoice(String str) {
		People pObj=mc.getPersonDetail(str);
    	name=pObj.getUserName();
        status = pObj.getStatus();
        gender = pObj.getSex();
        age = String.valueOf(pObj.getAge());
        state = pObj.getState();
        imgPath=pObj.getImgPath().replace("\"","");
        mom="";
        dad="";
        if(Integer.parseInt(age)>=minAdultAge) {
        	mom="No data";
        	dad="No data";
        }else {
        	ArrayList<String> parentName=mc.getParentName(str, "Parent");
	        System.out.println(parentName+"ParentName");
        	int size=parentName.size();
        	String[] sourceParentName=(String[]) parentName.toArray(new String[size]);
	        String name1=sourceParentName[0];
	        String name2=sourceParentName[1];
	        if(mc.checkGender(name1).equalsIgnoreCase("M")) {
	        	dad=name1;
	        	mom=name2;
	        }else {
	        	mom=name1;
	        	dad=name2;
	        }
        }
	}
	
	//This interface shows every people's detail info.
	public DetailInfoInterface(String str, mainControl mc){
		this.mc=mc;
		this.getPeopleInfoChoice(str);	//Get all data of one person that user clicked one data in the table on display management interface
		//Intiatilze defined elements
		jl2_title=new JLabel("People basic Info ", SwingConstants.CENTER);
		jl2_title.setFont(new   java.awt.Font("Dialog",   1,   20));
		jl2_name=new JLabel("Name: "+ name, SwingConstants.LEFT);
		jl2_name.setFont(new   java.awt.Font("Dialog",   0,   17));   
		jl2_status=new JLabel("State: "+ status, SwingConstants.LEFT);
		jl2_status.setFont(new   java.awt.Font("Dialog",   0,   17));   
		jl2_gender=new JLabel("Gender: " + gender, SwingConstants.LEFT);
		jl2_gender.setFont(new   java.awt.Font("Dialog",   0,   17));   
		jl2_age=new JLabel("Age: " + age, SwingConstants.LEFT);
		jl2_age.setFont(new   java.awt.Font("Dialog",   0,   17));   
		jl2_state=new JLabel("State: "+ state, SwingConstants.LEFT);
		jl2_state.setFont(new   java.awt.Font("Dialog",   0,   17));   
		jl3_photo=new JLabel("People photo", SwingConstants.CENTER);
		jl3_photo.setFont(new   java.awt.Font("Dialog",   1,   20));
		if(imgPath.equals("")) {
			this.jl3_img=new JLabel("No relative photo", SwingConstants.CENTER);
			this.jl3_img.setFont(new Font("Dialog",   0,   17));   
			
		}else {
			if(!CheckPhoto.verifiedHasStored(imgPath)) {
				this.jl3_img=new JLabel("No relative photo", SwingConstants.CENTER);
				this.jl3_img.setFont(new Font("Dialog",   0,   17));   
			}else {
				ImageIcon select=new ImageIcon(imgPath);
				select.setImage(select.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
				this.jl3_img=new JLabel(select);
			}
		}
		jl5_parentName=new JLabel(name+ "'s Parent Name", SwingConstants.CENTER);
		jl5_parentName.setFont(new   java.awt.Font("Dialog",   1,   20));
		 jl5_mom=new JLabel("Mom name: " + mom, SwingConstants.LEFT);
		 jl5_mom.setFont(new   java.awt.Font("Dialog",   0,   17));
		 jl5_dad=new JLabel("Dad name: "+ dad, SwingConstants.LEFT);
		 jl5_dad.setFont(new   java.awt.Font("Dialog",   0,   17));
		 jl6_childName=new JLabel(name+ "'s Children", SwingConstants.CENTER);
		 jl6_childName.setFont(new   java.awt.Font("Dialog",   1,   20));
		 
		 jlist1=new JList();
			//int minAdultAge=17;
			if(Integer.parseInt(age)>=minAdultAge) {
				this.mc.loadData(jlist1,name,"Parent");
			}else {			
				String[] noData=new String[1];
				noData[0]="No data";
				ListModel jListModel =  new DefaultComboBoxModel(noData);  
				jlist1.setModel(jListModel);
			}
			jsp=new JScrollPane();
			jsp.setViewportView(jlist1);
			
			jb6_close=new JButton("Close");
			jb6_close.addMouseListener(this);
		 
		//Define jpanel
		jp1=new JPanel(new BorderLayout());
		jp2=new JPanel(new GridLayout(6,1));
		jp3=new JPanel(new BorderLayout());
		jp4=new JPanel(new BorderLayout());
		jp5=new JPanel(new BorderLayout());
		jp6=new JPanel(new BorderLayout());
		jp7=new JPanel(new GridLayout(2,1));
		jp8=new JPanel();
		jp8.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//jpanel put into jsp
		jsp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jp2, jp3);
		//Define one part has how many pixels
		jsp1.setDividerLocation(300);
		//borderline is zero
		jsp1.setDividerSize(0);
		
		jsp2=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jp5, jp6);
		//Define one part has how many pixels
		jsp2.setDividerLocation(300);
		//borderline is zero
		jsp2.setDividerSize(0);
		//put two jsp above into jpanel
		
		//put elements into jpanel
		jp2.add(jl2_title);
		jp2.add(jl2_name);
		jp2.add(jl2_status);
		jp2.add(jl2_gender);
		jp2.add(jl2_age);
		jp2.add(jl2_state);
		jp3.add(jl3_photo, "North");
		jp3.add(jl3_img, "Center");
		
		jp7.add(jl5_mom,"Center");
		jp7.add(jl5_dad,"Center");
		
		jp5.add(jl5_parentName,"North");
		jp5.add(jp7,"Center");
		
		jp6.add(jl6_childName,"North");
		jp6.add(jsp,"Center");
		
		
		//put jsp into jp1
		jp1.add(jsp1,"Center");
		jp4.add(jsp2,"Center");
		jp8.add(jb6_close);
		//set this class as jdialog layout is borderLayout
		this.setLayout(new BorderLayout());
		
		//put jp1 into jdialog
		this.add(jp1,"North");
		this.add(jp4,"Center");
		this.add(jp8, "South");
		
	
		//set size and location
		int width=650;
		int height=450;
		this.setSize(width, height);
		Toolkit tk = this.getToolkit();
		Dimension dm=tk.getScreenSize();
		this.setLocation((int)(dm.getWidth()-width)/2, (int)(dm.getHeight()-height)/2);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource()==this.jb6_close) {
			this.dispose();
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

package tools;
/**
 *@ This is a JPanel, that can add one img to as background. Maybe we need to make many JPanels with its own background
 * @author Shunhe Wang s3587669
 *
 */
import javax.swing.JPanel;
import java.awt.*;

public class ImagePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	Image im;
	public ImagePanel(Image im) {
		this.im=im;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w,h);
	}
	
	//Draw the background
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(im, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

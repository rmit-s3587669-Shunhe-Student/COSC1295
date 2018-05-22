package tools;
/**
 * @author Shunhe Wang s3587669
 */

import javax.swing.JOptionPane;

public abstract class Message {
	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);							//Print out message
	}
}

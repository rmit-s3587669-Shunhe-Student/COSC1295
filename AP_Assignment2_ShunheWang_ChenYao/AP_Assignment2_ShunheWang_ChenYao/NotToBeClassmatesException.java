package myException;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

public class NotToBeClassmatesException extends Exception{

	private static final long serialVersionUID = 1L;
	public NotToBeClassmatesException(String mes) {
		super(mes);
	}
}
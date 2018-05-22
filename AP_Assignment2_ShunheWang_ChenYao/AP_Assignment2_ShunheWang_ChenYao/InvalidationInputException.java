package myException;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

public class InvalidationInputException extends Exception{

	private static final long serialVersionUID = 1L;
	public InvalidationInputException(String mes) {
		super(mes);
	}
}

package myException;

/**
 * 
 * @author Chen Yao s3373565
 *
 */
public class EmptyInputException extends Exception{

	private static final long serialVersionUID = 1L;
	public EmptyInputException(String mes) {
		super(mes);
	}
}

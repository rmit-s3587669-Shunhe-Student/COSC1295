package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */

public class NoSuchAgeException extends Exception{

	private static final long serialVersionUID = 1L;
	public NoSuchAgeException(String mes) {
		super(mes);
	}
}

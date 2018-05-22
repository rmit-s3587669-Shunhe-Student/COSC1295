package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */

public class NoParentException extends Exception{

	private static final long serialVersionUID = 1L;
	public NoParentException(String mes) {
		super(mes);
	}
}

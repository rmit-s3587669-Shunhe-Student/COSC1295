package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */
public class NoAvailableException extends Exception{

	private static final long serialVersionUID = 1L;
	public NoAvailableException(String mes) {
		super(mes);
	}
}
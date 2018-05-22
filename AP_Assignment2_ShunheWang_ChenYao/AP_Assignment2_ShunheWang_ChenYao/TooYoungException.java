package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */
public class TooYoungException extends Exception{

	private static final long serialVersionUID = 1L;
	public TooYoungException(String mes) {
		super(mes);
	}
}
package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */
public class NotToBeFriendException extends Exception{

	private static final long serialVersionUID = 1L;
	public NotToBeFriendException(String mes) {
		super(mes);
	}
}
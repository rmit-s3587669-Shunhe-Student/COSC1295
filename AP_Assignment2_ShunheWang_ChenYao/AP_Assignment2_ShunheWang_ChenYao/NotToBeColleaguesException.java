package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */
public class NotToBeColleaguesException extends Exception{

	private static final long serialVersionUID = 1L;
	public NotToBeColleaguesException(String mes) {
		super(mes);
	}
}
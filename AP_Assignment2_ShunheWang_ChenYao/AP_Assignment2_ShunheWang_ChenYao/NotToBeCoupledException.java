package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */

public class NotToBeCoupledException extends Exception{

	private static final long serialVersionUID = 1L;
	public NotToBeCoupledException(String mes) {
		super(mes);
	}
}
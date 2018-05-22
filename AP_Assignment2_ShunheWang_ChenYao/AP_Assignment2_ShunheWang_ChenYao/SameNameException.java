package myException;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */

public class SameNameException extends Exception{

	private static final long serialVersionUID = 1L;
	public SameNameException(String mes){
		super(mes);
	}
}

package myException;
/**
 * 
 * @author Chen Yao s3373565
 *
 */

public class ExistedParentRelationException extends Exception{

	private static final long serialVersionUID = 1L;
	public ExistedParentRelationException(String mes) {
		super(mes);
	}
}
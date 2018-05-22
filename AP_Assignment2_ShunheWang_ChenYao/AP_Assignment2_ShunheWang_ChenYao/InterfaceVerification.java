package tools;
/**
 * 
 * @author Shunhe Wang s3587669
 *
 */
public abstract class InterfaceVerification {
	
	// check the interface input is empty
	public static boolean isEmpty(String str) {
		if (str.equals("")) {
			return false;
		}
		return true;
	}

	// check the interface input is String
	public static boolean isString(String str) {
		if (str.matches("[a-zA-Z]+")) {
			return true;
		}
		return false;
	}

	// Check the interface input is number only
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// Check the age is in 0-150
	public static boolean inAgeRange(String str) {
		int minAge = 0;
		int maxAge = 150;
		int age = Integer.parseInt(str);
		if (age >= minAge && age <= maxAge) {
			return true;
		}
		return false;
	}
}

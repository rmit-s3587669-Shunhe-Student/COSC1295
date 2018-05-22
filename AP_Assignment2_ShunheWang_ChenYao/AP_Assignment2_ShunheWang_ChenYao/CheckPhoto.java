package tools;
/**
 * @author Shunhe Wang s3587669
 */
import java.io.File;

public class CheckPhoto {

	// Check the person photo img has stored into photo folder
	public static boolean verifiedHasStored(String photoPath) {
		try {
			File file = new File("photo");
			String[] fileName = file.list();
			for (int i = 0; i < fileName.length; i++) {
				String path = "photo//" + fileName[i];
				if (photoPath.equals(path)) {
					return true;
				}
			}
		} catch (Exception e) {
			Message.showMessage("No photo in photo database");
		}
		return false;
	}
}

package util;

public class InputValidator {
	
	public static boolean isValidPassword(String passwordInput) {
		    return passwordInput != null && passwordInput.matches("\\d{5}") && (Integer.parseInt(passwordInput) >= 10000 && Integer.parseInt(passwordInput) <= 99999);
		}
	
	public static boolean isValidPassword(int password) {
	    return password >= 10000 && password <= 99999;
	}
	
	public static boolean isValidName(String name) {
		if(name == null || name.isBlank()) {
			return false;
		}
		
		//Accented letters and spaces
		return name.matches("[\\p{L} '\\-]+");
	}
}

package aamain;

public class YouDied{
	
	public YouDied(String message, String className, int roughLineNumber) {
		System.out.println(message);
		System.out.println("(" + className + ", ~line " + roughLineNumber + ")");
		System.exit(0);
	}

}

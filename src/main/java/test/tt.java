package test;

public class tt {
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		System.out.println(sb.toString().equals(""));
		String ss = "z z ";
		System.out.println(ss.substring(0, ss.length() - 1).replace(' ', ','));
	}
}

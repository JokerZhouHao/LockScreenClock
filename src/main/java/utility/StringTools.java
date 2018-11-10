package utility;

/**
 * provide some function to deal string
 * @author ZhouHao
 * @since 2018年10月30日
 */
public class StringTools {
	// eg: "ZhouHao" to "Zhou,Hao"
	public static String splitUpperString(String str) {
		StringBuffer sb = new StringBuffer();
		int start = 0;
		char c ;
		for(int i=0; i<str.length(); i++) {
			c = str.charAt(i);
			if(i != 0 && c >= 'A' && c <= 'Z') {
				sb.append(str.substring(start, i));
				sb.append(',');
				start = i;
			}
		}
		sb.append(str.substring(start));
		sb.append(',');
		return sb.toString();
	}
	
	public static int indexOf(String str, char ch, int which) {
		if(which > 0) {
			int index = -1;
			while(which > 0) {
				index++;
				index = str.indexOf(ch, index);
				if(-1 == index)	return -1;
				which--;
			}
			return index;
		} else {
			int index = str.length();
			while(which < 0) {
				index--;
				for(; index>=0; index--) {
					if(str.charAt(index) == ch)	break;
				}
				if(index < 0)	return -1;
				which++;
			}
			return index;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(StringTools.splitUpperString("Zhou"));
		System.out.println(StringTools.indexOf("1zhou1zhou1zhou", '1', -3));
	}
	
}

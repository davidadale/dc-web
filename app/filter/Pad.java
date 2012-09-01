package filter;

public class Pad{
	public static String rpad(String s, int n ){
		return String.format("%1$-" + n + "s", s);
	}
	public static String lpad(Long l, int n){
		return String.format("%0" + n + "d", l);
	}
}

package cbuu.minet.util;

public class DebugLog {

	public static void log(String msg) {
		System.out.println(msg);
	}
	
	public static void log(String tag,String msg) {
		System.out.println(tag + "---------" + msg);
	}

}

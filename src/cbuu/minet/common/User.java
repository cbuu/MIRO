package cbuu.minet.common;

public class User {
	public static final int DEFAULT_ICON = 0;
	
	public static final int STATE_ONLINE = 1;
	public static final int STATE_OFFLINE = 0;

	private String username = null;
	private String ip = null;
	private int state = STATE_OFFLINE;

	private int iconNum = DEFAULT_ICON;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String ip) {
		this.username = username;
		this.ip = ip;
	}

	public int getIconNum() {
		return iconNum;
	}

	public void setIconNum(int iconNum) {
		this.iconNum = iconNum;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

}

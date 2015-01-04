package cbuu.minet.managers;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import cbuu.minet.common.User;
import cbuu.minet.database.DaoFactory;
import cbuu.minet.database.UserDao;

public class UserManager {

	private static UserManager instance = null;

	//a container of online users
	private HashMap<String,User> userMap = null;
	private UserDao userDao = null;
	
	public static UserManager getInstance() throws SQLException, Exception {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private UserManager() throws SQLException, Exception {
		userMap = new HashMap<String,User>();
		userDao = DaoFactory.creatUserDao();
	}
	
	public int addUser(User user) {
		if(user!=null){
			//userMap.put(user.getUsername(), user);
			return userDao.addUser(user);
		}else {
			return 0;
		}
	}
	
	public int deleteUserByUsername(String username) throws SQLException, Exception {
		//userMap.remove(username);
		return userDao.deleteUserByUsername(username);
	}
	
	public User getUserByUsername(String username) {
		//return userMap.get(username);'
		return userDao.queryUserByUsername(username);
	}
	
//	public HashMap<String, User> getUserMap() {
//		return userMap;
//		
//	}
	
	public ArrayList<User> getUserList(){
		return userDao.queryUsersForList();
	}
}

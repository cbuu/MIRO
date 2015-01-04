package minet.cbuu.test;

import java.sql.SQLException;
import java.util.ArrayList;

import cbuu.minet.common.User;
import cbuu.minet.database.DaoFactory;
import cbuu.minet.database.UserDao;
import cbuu.minet.managers.DBManager;
import cbuu.minet.util.DebugLog;

public class TestDB {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		User  user = new User();
		user.setUsername("CBUU");
		user.setIconNum(1);
		user.setIP("198.168.1.9");
		
		// TODO Auto-generated method stub
		DBManager.getInstance().initMySQL();
		
		UserDao userDao = DaoFactory.creatUserDao();
		
		//userDao.addUser(user);
		//userDao.updateUser(user);
		//User user2 = userDao.queryUserByUsername("iori");
		//ArrayList<User> list = userDao.queryUsersForList();
		//DebugLog.log(list.get(1).getUsername());
		userDao.deleteUserByUsername("CBUU");
		
	}

}

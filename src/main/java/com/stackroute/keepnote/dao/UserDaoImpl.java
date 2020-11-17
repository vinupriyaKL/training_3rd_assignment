package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
SessionFactory sessionFactory;
@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
	this.sessionFactory=sessionFactory;

	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		sessionFactory.getCurrentSession().save(user);

		return false;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {


User usertoFind= getUserById(user.getUserId());
if(usertoFind!=null) {
	usertoFind.setUserMobile(user.getUserMobile());
	sessionFactory.getCurrentSession().update(usertoFind);
	
}
return true;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		
			User userItem=  (User)sessionFactory.getCurrentSession().createQuery("from User where userId='" + UserId +"'").uniqueResult();
			return userItem;

		
		
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		String Password=(String)sessionFactory.getCurrentSession().createQuery("select userPassword from User where userId='" + userId +"'").uniqueResult();
		if(password.equals(Password)) {
			return true;
		}
		else {
			throw new UserNotFoundException("User not found");

		}

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId)  {
		User userDetails=getUserById(userId);
		if(userDetails == null) {
			return false;

		}
		else {
			sessionFactory.getCurrentSession().delete(userDetails);
			return true;
		}

		
		

	}

}

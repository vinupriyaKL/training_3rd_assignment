package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */ 

@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	private UserDAO userDAO;
	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO=userDAO;
	}

	/*
	 * This method should be used to save a new user.
	 */
	public boolean registerUser(User user) throws UserAlreadyExistException {
		User userValue=userDAO.getUserById(user.getUserId());
		if(userValue==null) {
			userDAO.registerUser(user);
			return true;

		}
		else {
			throw new UserAlreadyExistException("user already exists");


		}

	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User userWithNewVaue, String userId) throws Exception {
		User userWithExistingValue=userDAO.getUserById(userWithNewVaue.getUserId());
		if(userWithExistingValue==null) {
			throw new UserNotFoundException("user not found");
		}
		else {
			userDAO.updateUser(userWithNewVaue);
		}
		return userWithNewVaue;
	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String UserId) throws UserNotFoundException {
		User user=userDAO.getUserById(UserId);
		if(user!=null) {
			return user;

		}
		else {
			throw new UserNotFoundException("User Not Found");

		}

	}
	
	
	

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userName, String password) throws UserNotFoundException {
		
		Boolean status=userDAO.validateUser(userName, password);
		if(status) {
			return true;
		}
		else {
			throw new UserNotFoundException("User Not Found");
		}
	
			
	


	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId){
		
		return userDAO.deleteUser(UserId);
		

	}

}

package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;


/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation.
 * Annotate class with @SessionAttributes this  annotation is used to store the model attribute in the session.
 */

@RestController
@ComponentScan(basePackages="com.stackroute.keepnote")

public class UserAuthenticationController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	UserService userService;
@Autowired
	public UserAuthenticationController(UserService userService) {
		this.userService=userService;
	}


	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the userId and password
	 * and validating the same. Post login, the userId will have to be stored into
	 * session object, so that we can check whether the user is logged in for all
	 * other services handle UserNotFoundException as well. This handler method
	 * should return any one of the status messages basis on different situations:
	 * 1. 200(OK) - If login is successful. 2. 401(UNAUTHORIZED) - If login is not
	 * successful
	 * 
	 * This handler method should map to the URL "/login" using HTTP POST method
	 */


@PostMapping("/login")
public ResponseEntity<?> saveUser(@RequestBody User user,HttpSession session) throws UserNotFoundException
{
	
	session.setAttribute("loggedInUserId", user.getUserId());
	session.setAttribute("myUserPwd", user.getUserPassword());

	Boolean validateUser=userService.validateUser(user.getUserId(), user.getUserPassword());
	if(validateUser) {
		return new ResponseEntity<String>("Login successfull",HttpStatus.OK);

	}
	else {
		return new ResponseEntity<String>("Login unsuccessfull",HttpStatus.UNAUTHORIZED);

	}
		
	
	
}


@GetMapping("/logout")
public ResponseEntity<?> logoutUser(HttpSession session) throws UserNotFoundException
{
	
	if(session.getAttribute("loggedInUserId")!=null) {
		
		User userDetails=userService.getUserById(session.getAttribute("loggedInUserId").toString());
			
		Boolean validateUser=userService.validateUser(userDetails.getUserId(), userDetails.getUserPassword());
		if(validateUser) {
			session.removeAttribute("loggedInUserId");
			session.removeAttribute("myUserPwd");
			return new ResponseEntity<String>("logout successfull",HttpStatus.OK);

		}
		else {
			return new ResponseEntity<String>("logout unsuccessfull",HttpStatus.BAD_REQUEST);

		}
		
	}
	else {
		return new ResponseEntity<String>("logout unsuccessfull",HttpStatus.BAD_REQUEST);

	}


}



	/*
	 * Define a handler method which will perform logout. Post logout, the user
	 * session is to be destroyed. This handler method should return any one of the
	 * status messages basis on different situations: 1. 200(OK) - If logout is
	 * successful 2. 400(BAD REQUEST) - If logout has failed
	 * 
	 * This handler method should map to the URL "/logout" using HTTP GET method
	 */



}

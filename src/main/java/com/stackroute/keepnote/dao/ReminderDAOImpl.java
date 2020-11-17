package com.stackroute.keepnote.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Reminder;
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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	SessionFactory sessionFactory;
@Autowired
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;

	}
@Autowired
HttpSession session;
	/*
	 * Create a new reminder
	 */




public boolean createReminder(Reminder reminder) {
	Serializable savedReminder=sessionFactory.getCurrentSession().save(reminder);
	if(savedReminder!=null) {
		return true;
	}
	else {
		return false;

	}

}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		try {
			Reminder usertoFind= getReminderById(reminder.getReminderId());
			if(usertoFind!=null) {
				usertoFind.setReminderDescription(reminder.getReminderDescription());
				usertoFind.setReminderName(reminder.getReminderName());
				usertoFind.setReminderType(reminder.getReminderType());
				sessionFactory.getCurrentSession().update(usertoFind);
				
			}
			return true;

		}
		catch(Exception e) {
			return false;
		}
		

			}

	

	/*
	 * Remove an existing reminder
	 */

	public boolean deleteReminder(int reminderId) {
		try {
			Reminder rem=getReminderById(reminderId);
			sessionFactory.getCurrentSession().delete(rem);
			return true;
						
		}
		catch(ReminderNotFoundException e) {
			return false;

		}

	}
		
		

	

	/*
	 * Retrieve details of a specific reminder
	 */

	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminderFound=(Reminder)sessionFactory.getCurrentSession().createQuery("from Reminder where reminderId='"+reminderId +"'").uniqueResult();
		if(reminderFound!=null) {
			return reminderFound;

		}
		else {
			throw new ReminderNotFoundException("Reminder not found");
		}

	}
	
	
	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) {

		return sessionFactory.getCurrentSession().createQuery("from Reminder where reminderCreatedBy='"+userId +"'").list();

	}

}

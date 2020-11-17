package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

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
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	NoteDAO noteDAO;
	CategoryDAO categoryDAO;
	ReminderDAO reminderDAO;
	
	@Autowired
	public NoteServiceImpl(NoteDAO noteDAO,CategoryDAO categoryDAO,ReminderDAO reminderDAO) {
		
			this.categoryDAO=categoryDAO;
			this.noteDAO=noteDAO;
			this.reminderDAO=reminderDAO;
	}

	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException{
		
		Category categoryCheck=null;
		Reminder reminderCheck=null;


		try {
			Category cat=note.getCategory();
			Reminder rem=note.getReminder();

			if(cat!=null) {
				categoryCheck=categoryDAO.getCategoryById(cat.getCategoryId());

			}
			if(rem!=null) {
				reminderCheck=reminderDAO.getReminderById(rem.getReminderId());
			}
			if(noteDAO.createNote(note)) {
				return true;
			}
			else {
				return false;
			}
		

		}
			catch(ReminderNotFoundException e){
				throw new ReminderNotFoundException("Exception e");
				
			}
			catch(CategoryNotFoundException e) {
				throw new CategoryNotFoundException("Exception e");

				
			}
		

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) {
		
		
		return noteDAO.deleteNote(noteId);
		
		

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return noteDAO.getAllNotesByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note=noteDAO.getNoteById(noteId);
		if(note!=null) {
			return note;

		}
		else {
			throw new NoteNotFoundException("Note not found");
		}

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, CategoryNotFoundException ,NoteNotFoundException{
		Category categoryCheck=null;
		Reminder reminderCheck=null;


		try {
			Category cat=note.getCategory();
			Reminder rem=note.getReminder();
			Note noteFound=noteDAO.getNoteById(id);

			if(cat!=null) {
				categoryCheck=categoryDAO.getCategoryById(cat.getCategoryId());

			}
			if(rem!=null) {
				reminderCheck=reminderDAO.getReminderById(rem.getReminderId());
			}
			if(noteFound!=null) {
				noteDAO.UpdateNote(note);

			}

		}
		catch(ReminderNotFoundException e){
			throw new ReminderNotFoundException("Exception e");
			
		}
		catch(CategoryNotFoundException e) {
			throw new CategoryNotFoundException("Exception e");

			
		}
		catch(NoteNotFoundException e) {
			throw new NoteNotFoundException("Exception e");

		}
		
		
	
		
		return note;

	}

}

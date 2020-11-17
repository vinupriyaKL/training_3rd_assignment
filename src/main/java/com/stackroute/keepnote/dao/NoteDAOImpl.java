package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	SessionFactory sessionFactory;

	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
this.sessionFactory=sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		
		sessionFactory.getCurrentSession().save(note);
		
		return true;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		try {
	
			Note noteToDelete=getNoteById(noteId);
			sessionFactory.getCurrentSession().delete(noteToDelete);
			return true;

		}
		catch(NoteNotFoundException e) {
			return false;
		}
			
		}
		

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		return sessionFactory.getCurrentSession().createQuery("from Note where createdBy='"+userId +"'").list();

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		
		Note noteFound=(Note)sessionFactory.getCurrentSession().createQuery("from Note where noteId='"+noteId +"'").uniqueResult();

		if(noteFound!=null) {
			return noteFound;

		}
		else {
			throw new NoteNotFoundException("Note not found");
		}
		
		
	}

	/*
	 * Update an existing note
	 */

	
	public boolean UpdateNote(Note note) {
		try {
			Note usertoFind= getNoteById(note.getNoteId());
			if(usertoFind!=null) {
				sessionFactory.getCurrentSession().update(note);
				
			}
			return true;

		}
		catch(NoteNotFoundException e) {
			return false;
		}
		

			}


}

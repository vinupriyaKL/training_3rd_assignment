package com.stackroute.keepnote.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;


/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
@ComponentScan(basePackages="com.stackroute.keepnote")

public class NoteController {
	
	
	NoteService noteService;

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService=noteService;

	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in a Note table
	 * in the database.Handle ReminderNotFoundException and
	 * CategoryNotFoundException as well. please note that the loggedIn userID
	 * should be taken as the createdBy for the note.This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 201(CREATED) - If the note created successfully. 2. 409(CONFLICT) - If the
	 * noteId conflicts with any existing user3. 401(UNAUTHORIZED) - If the user
	 * trying to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/note" using HTTP POST method
	 */
	
	
	@PostMapping("/note") 
	
	
	public ResponseEntity<?> addNote(@RequestBody Note note, HttpSession session) throws ReminderNotFoundException,CategoryNotFoundException
	{ 
		
		if (session.getAttribute("loggedInUserId") == null) {
			return new ResponseEntity<>("User not authorized.Please do login", HttpStatus.UNAUTHORIZED);
		}
		if(noteService.createNote(note)) {
			return new ResponseEntity<>("Note Created", HttpStatus.CREATED);

		}
		else {
			return new ResponseEntity<>("Note Already Exists", HttpStatus.CONFLICT);

		}
		
		}

	/*
	 * Define a handler method which will delete a note from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */

	
	@DeleteMapping("note/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable("id") int noteId , HttpSession session) throws NoteNotFoundException {

		
		if(session.getAttribute("loggedInUserId")==null) {
			return new ResponseEntity<String>("please do login first",HttpStatus.UNAUTHORIZED);

		}
		
		if (noteService.deleteNote(noteId)) {
			return new ResponseEntity<>("Note created successfully", HttpStatus.OK);
		} 
		else if(noteService.deleteNote(noteId)==false) {
			return new ResponseEntity<>("Note Not Found", HttpStatus.NOT_FOUND);

		}
			else {
			return new ResponseEntity<>("Note already exists", HttpStatus.CONFLICT);
		}
		

		}
		
	
	
	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * note table in database handle ReminderNotFoundException,
	 * NoteNotFoundException, CategoryNotFoundException as well. please note that
	 * the loggedIn userID should be taken as the createdBy for the note. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note updated successfully. 2.
	 * 404(NOT FOUND) - If the note with specified noteId is not found. 3.
	 * 401(UNAUTHORIZED) - If the user trying to perform the action has not logged
	 * in.
	 * 
	 * This handler method should map to the URL "/note/{id}" using HTTP PUT method.
	 */
	
	@PutMapping("/note/{id}")
	public ResponseEntity<String> updateNote(@RequestBody Note note, HttpSession session,@PathVariable("id") int noteId) throws CategoryNotFoundException,NoteNotFoundException,ReminderNotFoundException {

		
		
		if(session.getAttribute("loggedInUserId")==null) {
			return new ResponseEntity<String>("please do login first",HttpStatus.UNAUTHORIZED);

		}
		
		
		if (noteService.updateNote(note, noteId)!=null) {
			return new ResponseEntity<>("Note created successfully", HttpStatus.OK);
		} 
		else if(noteService.updateNote(note, noteId)==null) {
			return new ResponseEntity<>("Note Not Found", HttpStatus.NOT_FOUND);

		}
			else {
			return new ResponseEntity<>("Note already exists", HttpStatus.CONFLICT);
		}
		

		}
	
	
	@GetMapping("/note")
	public ResponseEntity<?> getNoteByUser(HttpSession session) throws NoteNotFoundException{
		if(session.getAttribute("loggedInUserId")!=null) {
			String user=session.getAttribute("loggedInUserId").toString();
			List<Note> noteList=noteService.getAllNotesByUserId(user);
			if(noteList!=null && noteList.size()>0) {
		  		return new ResponseEntity<List<Note>>(noteList,HttpStatus.OK);

		}
			else {
				throw new NoteNotFoundException("Note not found");
			}
		}
		else {
			return new ResponseEntity<String>("please do login first",HttpStatus.UNAUTHORIZED);
		}
		
		
		
	
	}

}

	/*
	 * Define a handler method which will get us the notes by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/note" using HTTP GET method
	 */



package com.stackroute.keepnote.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	
	SessionFactory sessionFactory;
	@Autowired
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;

	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Serializable categoryObject=sessionFactory.getCurrentSession().save(category);
		if(categoryObject!=null) {
			return true;
		}
		else {
			return false;

		}

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		try {
			Category categoryToBeDeleted=getCategoryById(categoryId);
			sessionFactory.getCurrentSession().delete(categoryToBeDeleted);
			return true;
						
		}
		catch(Exception e) {
			return false;

		}
	
	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		sessionFactory.getCurrentSession().update(category);
		return true;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category categoryFound=(Category)sessionFactory.getCurrentSession().createQuery("from Category where categoryId='"+categoryId +"'").uniqueResult();
		if(categoryFound!=null) {
			return categoryFound;

		}
		else {
			throw new CategoryNotFoundException("category not found");
		}

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		List<Category> categoryFoundByUser=sessionFactory.getCurrentSession().createQuery("from Category where categoryCreatedBy='"+ userId +"'").list();
		return categoryFoundByUser;
	}

}

package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryServiceImpl implements CategoryService {
	/*
	 * Autowiring should be implemented for the CategoryDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
	CategoryDAO categoryDAO;

	@Autowired(required = true)
	public CategoryServiceImpl(CategoryDAO categoryDAO) {
		this.categoryDAO=categoryDAO;
	}

	/*
	 * This method should be used to save a new category.
	 */
	public boolean createCategory(Category category) {
		Boolean status=categoryDAO.createCategory(category);
		return status;

	}

	/* This method should be used to delete an existing category. */
	public boolean deleteCategory(int categoryId) {
		Boolean status=categoryDAO.deleteCategory(categoryId);
		return status;

	}

	/*
	 * This method should be used to update a existing category.
	 */

	public Category updateCategory(Category category, int id) throws CategoryNotFoundException {
		try {
			Category categoryCheck=getCategoryById(id);
			categoryDAO.updateCategory(category);
			return category;

		}
		catch(CategoryNotFoundException categoryException){
			throw new CategoryNotFoundException("category not found");
		}

	}

	/*
	 * This method should be used to get a category by categoryId.
	 */
	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		
			Category category=categoryDAO.getCategoryById(categoryId);
			if(category!=null) {
				return category;

			}
			else {
				throw new CategoryNotFoundException("Category not found");
			}

	}

	/*
	 * This method should be used to get a category by userId.
	 */

	public List<Category> getAllCategoryByUserId(String userId) {
		return categoryDAO.getAllCategoryByUserId(userId);
		
		 

	}

}

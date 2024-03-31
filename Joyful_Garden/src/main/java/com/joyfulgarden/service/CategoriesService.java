package com.joyfulgarden.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyfulgarden.model.Categories;
import com.joyfulgarden.model.CategoriesRepositorya;

@Service
public class CategoriesService {

	@Autowired
	private final CategoriesRepositorya categoriesRepository;

	public CategoriesService(CategoriesRepositorya categoriesRepository) {
		this.categoriesRepository = categoriesRepository;
	}

	public List<Categories> getAllCategories() {
		return categoriesRepository.findAll();
	}

	public Optional<Categories> getCategoryById(Integer categoryID) {
		return categoriesRepository.findById(categoryID);
	}

	public Categories saveCategory(Categories category) {
		return categoriesRepository.save(category);
	}

	public void deleteCategory(Integer categoryID) {
		categoriesRepository.deleteById(categoryID);
	}
}

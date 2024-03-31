package com.joyfulgarden.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepositorya extends JpaRepository<Categories, Integer> {
    List<Categories> findByCategoryName(String categoryName);
}

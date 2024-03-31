package com.joyfulgarden.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORYID")
    private Integer categoryID;

    @Column(name = "CATEGORYNAME", nullable = false, length = 50)
    private String categoryName;

    @Column(name = "PARENTCATEGORYID")
    private Integer parentCategoryID;

    @ManyToOne
    @JoinColumn(name = "PARENTCATEGORYID", referencedColumnName = "CATEGORYID", insertable = false, updatable = false)
    private Categories parentCategory;

    // Getter and Setter methods

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(Integer parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    public Categories getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Categories parentCategory) {
        this.parentCategory = parentCategory;
    }
}

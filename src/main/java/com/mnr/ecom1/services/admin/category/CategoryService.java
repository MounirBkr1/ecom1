package com.mnr.ecom1.services.admin.category;

import com.mnr.ecom1.dto.CategoryDto;
import com.mnr.ecom1.entities.Category;

public interface CategoryService {
     Category createCategory(CategoryDto categoryDto);
}

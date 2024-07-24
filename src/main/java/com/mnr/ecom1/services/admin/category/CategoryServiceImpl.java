package com.mnr.ecom1.services.admin.category;


import com.mnr.ecom1.dto.CategoryDto;
import com.mnr.ecom1.entities.Category;
import com.mnr.ecom1.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }


}

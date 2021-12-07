package hangoutbk.services;

import hangoutbk.controllers.handlers.exceptions.model.ResourceNotFoundException;
import hangoutbk.dtos.CategoryDTO;
import hangoutbk.dtos.EventDTO;
import hangoutbk.dtos.PersonDTO;
import hangoutbk.dtos.builders.CategoryBuilder;
import hangoutbk.dtos.builders.EventBuilder;
import hangoutbk.dtos.builders.PersonBuilder;
import hangoutbk.entities.Category;
import hangoutbk.entities.Event;
import hangoutbk.entities.Person;
import hangoutbk.repositories.CategoryRepository;
import hangoutbk.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOS =new ArrayList<>();
        categories.forEach( u->{
            categoryDTOS.add(CategoryBuilder.toCategoryDTO(u));
        });

        return categoryDTOS;
    }

    public UUID insert(CategoryDTO categoryDTO) {
        Category category = CategoryBuilder.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        LOGGER.debug("Category with id {} was inserted in db", category.getId());
        return category.getId();
    }

    public CategoryDTO findCategoryByName(String name) {
        Category categoryOptional = categoryRepository.findByName(name);
        if (categoryOptional == null) {
            LOGGER.error("Category with name {} was not found in db", categoryOptional.getName());
            throw new ResourceNotFoundException(Category.class.getSimpleName() + " with id: " +  categoryOptional.getId());
        }
        return CategoryBuilder.toCategoryDTO(categoryOptional);
    }
}

package hangoutbk.controllers;

import hangoutbk.dtos.CategoryDTO;
import hangoutbk.dtos.EventDTO;
import hangoutbk.dtos.PersonDTO;
import hangoutbk.services.CategoryService;
import hangoutbk.services.EventService;
import hangoutbk.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/category")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getEvents() {
        List<CategoryDTO> dtos = categoryService.findCategories();
        LOGGER.info("searching for categories");
        return new ResponseEntity<>(dtos, HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        LOGGER.info("inserting category: {}", categoryDTO);
        UUID personID = categoryService.insert(categoryDTO);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }
}

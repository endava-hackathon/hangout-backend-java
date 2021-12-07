package hangoutbk.dtos.builders;

import hangoutbk.dtos.CategoryDTO;
import hangoutbk.entities.Category;
import lombok.Builder;

import java.util.UUID;

@Builder
public class CategoryBuilder {

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getName());
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        return new Category(UUID.randomUUID(),
            categoryDTO.getName());
    }
}

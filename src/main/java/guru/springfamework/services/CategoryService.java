package guru.springfamework.services;

import guru.springfamework.api.v1.model.CategoryDTO;

import java.util.Optional;
import java.util.stream.Stream;

public interface CategoryService {

    Stream<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryById(Long id);

    Stream<CategoryDTO> getCategoriesByName(String name);
}

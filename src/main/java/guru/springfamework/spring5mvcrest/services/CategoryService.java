package guru.springfamework.spring5mvcrest.services;

import guru.springfamework.spring5mvcrest.api.v1.model.CategoryDTO;

import java.util.Optional;
import java.util.stream.Stream;

public interface CategoryService {

    Stream<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryById(Long id);

    Stream<CategoryDTO> getCategoriesByName(String name);
}

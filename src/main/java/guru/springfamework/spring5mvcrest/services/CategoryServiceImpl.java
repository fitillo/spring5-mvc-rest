package guru.springfamework.spring5mvcrest.services;

import guru.springfamework.spring5mvcrest.api.v1.mapper.CategoryMapper;
import guru.springfamework.spring5mvcrest.api.v1.model.CategoryDTO;
import guru.springfamework.spring5mvcrest.domain.Category;
import guru.springfamework.spring5mvcrest.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Stream<CategoryDTO> getAllCategories() {
        return this.repository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream().map(mapper::categoryToCategoryDTO);
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        Optional<Category> category = this.repository.findById(id);

        return category.isEmpty() ? Optional.empty() : Optional.of(mapper.categoryToCategoryDTO(category.get()));
    }

    @Override
    public Stream<CategoryDTO> getCategoriesByName(String name) {
        List<Category> categories = this.repository.getCategoriesByNameLikeOrderByName(name+"%");

        return categories.isEmpty() ?
                Stream.empty() :
                categories.stream().map(mapper::categoryToCategoryDTO);
    }
}

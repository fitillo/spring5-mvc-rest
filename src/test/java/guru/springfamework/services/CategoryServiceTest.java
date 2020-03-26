package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    public static final long ID = 1L;
    public static final String FRUIT = "Fruit";
    public static final List<Category> categories = Arrays.asList(Category.builder().id(ID).name(FRUIT).build()
            , Category.builder().id(2L).name("").build(), Category.builder().id(3L).name("name").build());

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CategoryServiceImpl(repository, CategoryMapper.INSTANCE);
    }

    @Test
    void getAllCategories() {
        //given
        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(categories);

        //when
        Stream<CategoryDTO> dtoStream = service.getAllCategories();

        //then
        assertEquals(3, dtoStream.count());
        verify(repository).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void getAllCategoriesFilter() {
        //given
        when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(categories);

        //when
        Stream<CategoryDTO> dtoStream = service.getAllCategories();

        //then
        assertEquals(1, dtoStream.filter(dto -> dto.getId().equals(ID) && dto.getName().equals(FRUIT)).count());
        verify(repository).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void getCategoryById() {
        //given
        Category cat = new Category();
        cat.setId(ID);
        cat.setName(FRUIT);
        when(repository.findById(anyLong())).thenReturn(Optional.of(cat));

        //when
        Optional<CategoryDTO> dto = service.getCategoryById(ID);

        //then
        assertTrue(dto.isPresent());
        assertEquals(ID, dto.get().getId());
        assertEquals(FRUIT, dto.get().getName());
        verify(repository).findById(anyLong());
    }

    @Test
    void noCategoryById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Optional<CategoryDTO> dto = service.getCategoryById(ID);

        //then
        assertTrue(dto.isEmpty());
        verify(repository).findById(anyLong());
    }

    @Test
    void getCategoryByName() {
        //given
        when(repository.getCategoriesByNameLikeOrderByName(anyString())).thenReturn(Collections.singletonList(categories.get(0)));

        //when
        Stream<CategoryDTO> result = service.getCategoriesByName(FRUIT);

        //then
        List<CategoryDTO> dtos = result
                .filter(dto -> dto.getId().equals(ID) && dto.getName().equals(FRUIT))
                .collect(Collectors.toList());

        assertNotNull(dtos);
        assertEquals(1, dtos.size());
        assertEquals(ID, dtos.get(0).getId());
        assertEquals(FRUIT, dtos.get(0).getName());
        verify(repository).getCategoriesByNameLikeOrderByName(anyString());
    }

    @Test
    void noCategoryByName() {
        //given
        when(repository.getCategoriesByNameLikeOrderByName(anyString())).thenReturn(new ArrayList<>());

        //when
        Stream<CategoryDTO> dtos = service.getCategoriesByName(FRUIT);

        //then
        assertEquals(0, dtos.count());
        verify(repository).getCategoriesByNameLikeOrderByName(anyString());
    }
}
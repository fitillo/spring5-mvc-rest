package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController controller;

    @Mock
    private CategoryService service;

    private MockMvc mockMvc;

    public static final long ID = 1L;
    public static final String FRUIT = "Fruit";
    public static Stream<CategoryDTO> categories;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        categories = Stream.of(CategoryDTO.builder().id(ID).name(FRUIT).build(),
                CategoryDTO.builder().id(2L).name("").build(), CategoryDTO.builder().id(3L).name("name").build());
    }

    @Test
    void getAllCategories() throws Exception{
        when(service.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(3)));
        verify(service).getAllCategories();
    }

    @Test
    void testGetByNameCategories() throws Exception {
        when(service.getCategoriesByName(anyString())).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories/Fruit").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(3)))
                .andExpect(jsonPath("$.categories[0].name", is(FRUIT)))
                .andExpect(jsonPath("$.categories[0].id", is((int) ID)));
        verify(service).getCategoriesByName(anyString());
    }
}
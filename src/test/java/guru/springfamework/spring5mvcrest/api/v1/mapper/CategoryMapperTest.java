package guru.springfamework.spring5mvcrest.api.v1.mapper;

import guru.springfamework.spring5mvcrest.api.v1.model.CategoryDTO;
import guru.springfamework.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    final CategoryMapper mapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        //given, when
        CategoryDTO dto = mapper.categoryToCategoryDTO(Category.builder().id(1L).name("Fruit").build());

        //then
        assertNotNull(dto);
        assertEquals(1l, dto.getId());
        assertEquals("Fruit", dto.getName());
    }
}
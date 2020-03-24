package guru.springfamework.spring5mvcrest.api.v1.mapper;

import guru.springfamework.spring5mvcrest.api.v1.model.CategoryDTO;
import guru.springfamework.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String FRUIT = "Fruit";
    public static final long ID = 1l;
    final CategoryMapper mapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        //given, when
        CategoryDTO dto = mapper.categoryToCategoryDTO(Category.builder().id(ID).name(FRUIT).build());

        //then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(FRUIT, dto.getName());
    }
}
package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public class CategoryListDTO {

    private Stream<CategoryDTO> categories;
}

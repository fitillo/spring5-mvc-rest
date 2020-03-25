package guru.springfamework.spring5mvcrest.repositories;

import guru.springfamework.spring5mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> getCategoriesByNameLikeOrderByName(String name);
}

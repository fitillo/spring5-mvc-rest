package guru.springfamework.spring5mvcrest.controllers.v1;

import guru.springfamework.spring5mvcrest.api.v1.model.CategoryListDTO;
import guru.springfamework.spring5mvcrest.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO> getAllCategories() {
        return new ResponseEntity<>(new CategoryListDTO(service.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryListDTO> getCategoriesByName(@PathVariable String name) {
        return new ResponseEntity<>(
                new CategoryListDTO(service.getCategoriesByName(name)), HttpStatus.OK);
    }
}

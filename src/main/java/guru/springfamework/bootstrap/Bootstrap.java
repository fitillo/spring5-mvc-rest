package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().name("Fruits").build());
        categoryRepository.save(Category.builder().name("Dried").build());
        categoryRepository.save(Category.builder().name("Fresh").build());
        categoryRepository.save(Category.builder().name("Exotic").build());
        categoryRepository.save(Category.builder().name("Nuts").build());

        log.info(categoryRepository.count() + " categories loaded");
    }

    private void loadCustomers() {
        customerRepository.save(Customer.builder().firstName("Joe").lastName("Ingles").build());
        customerRepository.save(Customer.builder().firstName("Bojan").lastName("Bogdanovic").build());
        customerRepository.save(Customer.builder().firstName("Rudy").lastName("Gobert").build());
        customerRepository.save(Customer.builder().firstName("Donovan").lastName("Mitchell").build());
        customerRepository.save(Customer.builder().firstName("Mike").lastName("Conley").build());

        log.info(customerRepository.count() + " customers loaded");
    }
}

package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Stream<CustomerDTO> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::convert);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return repository.findById(id).map(mapper::convert);
    }
}

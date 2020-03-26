package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerDTOMapperToCustomer;
import guru.springfamework.api.v1.mapper.CustomerMapperToDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapperToDTO mapperToDTO;
    private final CustomerDTOMapperToCustomer mapperToCustomer;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapperToDTO mapperToDTO,
                               CustomerDTOMapperToCustomer mapperToCustomer) {
        this.repository = repository;
        this.mapperToDTO = mapperToDTO;
        this.mapperToCustomer = mapperToCustomer;
    }

    @Override
    public Stream<CustomerDTO> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapperToDTO::convert);
    }

    @Override
    public Optional<CustomerDTO> getCustomerDTOById(Long id) {
        return repository.findById(id).map(mapperToDTO::convert);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return mapperToDTO.convert(repository.save(mapperToCustomer.convert(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = mapperToCustomer.convert(customerDTO);
        customer.setId(id);
        return repository.findById(id).isEmpty()
                ? Optional.empty()
                : Optional.of(mapperToDTO.convert(repository.save(customer)));
    }
}

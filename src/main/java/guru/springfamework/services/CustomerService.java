package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.Optional;
import java.util.stream.Stream;

public interface CustomerService {

    Stream<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(Long id);
}

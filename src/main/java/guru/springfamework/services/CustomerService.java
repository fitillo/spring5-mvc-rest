package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.Optional;
import java.util.stream.Stream;

public interface CustomerService {

    Stream<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerDTOById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO);

    Optional<CustomerDTO> patchCustomer(Long id, CustomerDTO customerDTO);
}

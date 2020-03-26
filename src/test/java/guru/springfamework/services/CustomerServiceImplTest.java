package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerDTOMapperToCustomer;
import guru.springfamework.api.v1.mapper.CustomerMapperToDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static final long ID = 1l;
    private static final String BOB = "Bob";
    private static final String JOHNSON = "Johnson";
    private static final String URL = "/api/v1/customers";

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;

    private List<Customer> customers;

    private final CustomerMapperToDTO mapperToDTO = new CustomerMapperToDTO();
    private final CustomerDTOMapperToCustomer mapperToCustomer = new CustomerDTOMapperToCustomer();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mapperToDTO, "url", URL);
        ReflectionTestUtils.setField(mapperToCustomer, "url", URL);
        service = new CustomerServiceImpl(repository, mapperToDTO, mapperToCustomer);
        customers = Arrays.asList(Customer.builder().id(ID).firstName(BOB).lastName(JOHNSON).build(),
                Customer.builder().id(ID+1).firstName(BOB).lastName(JOHNSON).build());
    }

    @Test
    void getAllCustomers() {
        //given
        when(repository.findAll()).thenReturn(customers);

        //when
        Stream<CustomerDTO> stream = service.getAllCustomers();
        Stream<CustomerDTO> stream2 = service.getAllCustomers();

        //then
        assertNotNull(stream);
        assertEquals(2, stream.count());
        assertEquals(2, stream2.filter(customer -> customer.getFirstName().equals(BOB)).count());
        verify(repository, times(2)).findAll();
    }

    @Test
    void getCustomerById() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.of(customers.get(0)));

        //when
        Optional<CustomerDTO> customerDTO = service.getCustomerById(ID);

        //then
        assertNotNull(customerDTO);
        assertTrue(customerDTO.isPresent());
        assertEquals(BOB, customerDTO.get().getFirstName());
        assertEquals("/api/v1/customers/"+ID, customerDTO.get().getCustomerUrl());
        verify(repository).findById(anyLong());
    }

    @Test
    public void createNewCustomer() throws Exception {

        //given
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(BOB).build();
        Customer savedCustomer = Customer.builder().firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName()).id(ID).build();

        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = service.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
        verify(repository).save(any(Customer.class));
    }
}
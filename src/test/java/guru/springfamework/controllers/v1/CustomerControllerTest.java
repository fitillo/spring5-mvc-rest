package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.stream.Stream;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    public static final String CUSTOMERS_1 = "/api/v1/customers/1";
    public static final String CUSTOMERS = "/api/v1/customers";
    @InjectMocks
    private CustomerController controller;

    @Mock
    private CustomerService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAllCustomers() throws Exception {
        //given
        Stream<CustomerDTO> customers = Stream.of(CustomerDTO.builder().firstName("Bob").build(),
                CustomerDTO.builder().firstName("Jim").build());
        when(service.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(get(CUSTOMERS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
        verify(service).getAllCustomers();
    }

    @Test
    void getCustomerById() throws Exception {
        //given
        Optional<CustomerDTO> customerDTO = Optional.of(CustomerDTO.builder().firstName("Bob").build());
        when(service.getCustomerDTOById(anyLong())).thenReturn(customerDTO);

        //then
        mockMvc.perform(get(CUSTOMERS_1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Bob")));
        verify(service).getCustomerDTOById(anyLong());
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(service.createNewCustomer(ArgumentMatchers.any())).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                //.andReturn().getResponse().getContentAsString();
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMERS_1)));

        /*System.out.println(response);*/
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = CustomerDTO.builder().firstName("Fred").lastName("Flintstone").build();

        CustomerDTO returnDTO = CustomerDTO.builder().firstName(customer.getFirstName())
                .lastName(customer.getLastName()).customerUrl(CUSTOMERS_1).build();

        when(service.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(Optional.of(returnDTO));

        //when/then
        mockMvc.perform(put(CUSTOMERS_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMERS_1)));
    }

    @Test
    void updateCustomerNotFound() throws Exception {
        CustomerDTO customer = CustomerDTO.builder().firstName("Fred").lastName("Flintstone").build();
        when(service.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put(CUSTOMERS_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isNotFound());
    }
}
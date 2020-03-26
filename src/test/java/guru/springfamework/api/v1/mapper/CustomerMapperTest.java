package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
class CustomerMapperTest {

    private static final String BOB = "Bob";
    private static final long ID = 1l;
    private static final String JOHNSON = "Johnson";
    private final CustomerMapper mapper = new CustomerMapper();
    private static final String URL = "/api/v1/customers";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mapper, "url", URL);
    }

    @Test
    void customerToCustomerDTO() {
        //given, when
        CustomerDTO dto = mapper.convert(Customer.builder().id(ID).firstName(BOB).lastName(JOHNSON).build());

        //then
        assertNotNull(dto);
        assertEquals(BOB, dto.getFirstname());
        assertEquals(JOHNSON, dto.getLastname());
        assertEquals(URL+"/"+ID, dto.getCustomerUrl());
    }
}
package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperToDTO implements Converter<Customer, CustomerDTO> {

    @Value("${v1.customer.url}")
    private String url;

    @Override
    public CustomerDTO convert(Customer source) {
        return CustomerDTO.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .customerUrl(url+"/"+source.getId())
                .build();
    }

    /*@Mapping(target = "customerUrl", ignore = true)
    CustomerDTO customerToCustomerDTO(Customer customer);

    @AfterMapping
    default void calculateURL(Customer customer, CustomerDTO dto, @Value("${api.v1.customer.url}") String url) {
        dto.setCustomerUrl(url+"/"+customer.getId());
    }*/
}

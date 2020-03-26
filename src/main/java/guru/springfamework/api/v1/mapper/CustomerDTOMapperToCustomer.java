package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapperToCustomer implements Converter<CustomerDTO, Customer> {

    @Value("${v1.customer.url}")
    private String url;

    @Override
    public Customer convert(CustomerDTO source) {
        return Customer.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}

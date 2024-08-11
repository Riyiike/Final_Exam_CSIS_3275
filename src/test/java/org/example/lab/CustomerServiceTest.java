package org.example.lab;

import org.example.lab.Entities.Customer;
import org.example.lab.Repositories.CustomerRepository;
import org.example.lab.Services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        // Given
        Customer customer1 = new Customer(1, "John Doe", "john@example.com");
        Customer customer2 = new Customer(2, "Jane Doe", "jane@example.com");
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<Customer> result = customerService.getAllCustomers();

        // Then
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getCname());
        assertEquals("Jane Doe", result.get(1).getCname());
    }

    @Test
    void testGetCustomerById() {
        // Given
        Customer customer = new Customer(1, "John Doe", "john@example.com");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        // When
        Customer result = customerService.getCustomerById(1);

        // Then
        assertEquals("John Doe", result.getCname());
        assertEquals("john@example.com", result.getEmail());
    }
}
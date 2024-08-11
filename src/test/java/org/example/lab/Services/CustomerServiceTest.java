package org.example.lab.Services;

import org.example.lab.Entities.Customer;
import org.example.lab.Repositories.CustomerRepository;
import org.example.lab.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1, "John Doe", "john.doe@example.com", "Individual", 150.0, new Date(), 2023);
    }

    @Test
    void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> fetchedCustomers = customerService.getAllCustomers();

        assertEquals(1, fetchedCustomers.size());
        assertEquals("John Doe", fetchedCustomers.get(0).getCname());
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer fetchedCustomer = customerService.getCustomerById(1);

        assertNotNull(fetchedCustomer);
        assertEquals("John Doe", fetchedCustomer.getCname());
    }

    @Test
    void testUpdateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerService.updateCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1);

        customerService.deleteCustomer(1);

        verify(customerRepository, times(1)).deleteById(1);
    }
}
package org.example.lab.Services;

import org.example.lab.Entities.Customer;
import org.example.lab.Repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReserveSeat_Success() {
        // Arrange
        String seatNumber = "1A";
        String customerName = "John Doe";

        // Mock the repository behavior
        List<Customer> customers = Arrays.asList(
                new Customer(1, "Jane Doe", "2B", 0),
                new Customer(2, customerName, "", 0)
        );

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = customerService.reserveSeat(seatNumber, customerName);

        // Assert
        assertTrue(result, "Seat should be reserved successfully");

        // Verify interactions
        verify(customerRepository).findAll();
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void testReserveSeat_Failure_AlreadyReserved() {
        // Arrange
        String seatNumber = "1A";
        String customerName = "John Doe";

        // Mock the repository behavior
        List<Customer> customers = Arrays.asList(
                new Customer(1, "Jane Doe", seatNumber, 0),
                new Customer(2, customerName, "", 0)
        );

        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        boolean result = customerService.reserveSeat(seatNumber, customerName);

        // Assert
        assertFalse(result, "Seat should not be reserved as it is already taken");

        // Verify interactions
        verify(customerRepository).findAll();
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void testReserveSeat_Failure_CustomerNotFound() {
        // Arrange
        String seatNumber = "1A";
        String customerName = "John Doe";

        // Mock the repository behavior
        List<Customer> customers = Arrays.asList(
                new Customer(1, "Jane Doe", "", 0)
        );

        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        boolean result = customerService.reserveSeat(seatNumber, customerName);

        // Assert
        assertFalse(result, "Seat should not be reserved as the customer is not found");

        // Verify interactions
        verify(customerRepository).findAll();
        verify(customerRepository, never()).save(any(Customer.class));
    }
}

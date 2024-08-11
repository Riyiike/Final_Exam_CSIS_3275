package org.example.lab.Web;

import org.example.lab.Entities.Customer;
import org.example.lab.Services.CustomerService;
import org.example.lab.Web.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitialPage() {
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        String viewName = customerController.initialPage(model);

        assertEquals("Main", viewName);
        verify(model, times(1)).addAttribute(eq("customers"), any());
        verify(model, times(1)).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    public void testAddOrUpdateCustomer_ValidCustomer() {
        when(bindingResult.hasErrors()).thenReturn(false);

        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "individual", 1000.0, new Date(), 5);
        String viewName = customerController.addOrUpdateCustomer(customer, bindingResult, model);

        assertEquals("redirect:/", viewName);
        verify(customerService, times(1)).updateCustomer(customer);
    }

    @Test
    public void testAddOrUpdateCustomer_InvalidCustomer() {
        when(bindingResult.hasErrors()).thenReturn(true);

        Customer customer = new Customer();
        String viewName = customerController.addOrUpdateCustomer(customer, bindingResult, model);

        assertEquals("CustomerForm", viewName);
        verify(customerService, never()).addCustomer(any());
        verify(customerService, never()).updateCustomer(any());
    }

    @Test
    public void testEditCustomer() {
        Customer customer = new Customer(1, "John Doe", "john.doe@example.com", "individual", 1000.0, new Date(), 5);
        when(customerService.getCustomerById(1)).thenReturn(customer);

        String viewName = customerController.editCustomer(1, model);

        assertEquals("customerForm", viewName);
        verify(model, times(1)).addAttribute(eq("customer"), eq(customer));
    }

    @Test
    public void testDeleteCustomer() {
        String viewName = customerController.deleteCustomer(1, redirectAttributes);

        assertEquals("redirect:/", viewName);
        verify(customerService, times(1)).deleteCustomer(1);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("message"), any());
    }

    @Test
    public void testGetCompoundInterest() {
        String viewName = customerController.getCompoundInterest(1, 1000.0, 5, "individual", model);

        assertEquals("compound-interest", viewName);
        verify(model, times(1)).addAttribute(eq("compoundInterestResults"), any());
    }
}
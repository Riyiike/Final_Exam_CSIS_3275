package org.example.lab.Web;

import lombok.AllArgsConstructor;
import org.example.lab.Entities.Customer;
import org.example.lab.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService; // Use the service layer instead of repository

    @GetMapping(path = "/")
    public String initialPage(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customer", new Customer());
        return "Main";
    }

    @PostMapping("/addOrUpdateCustomer")
    public String addOrUpdateCustomer(@ModelAttribute Customer customer, Model model) {
        if (customer.getId() > 0) {
            // Update existing customer
            customerService.updateCustomer(customer);
        } else {
            // Add new customer
            customerService.addCustomer(customer);
        }

        // Refresh the list of customers to show the latest data in the view
        model.addAttribute("customers", customerService.getAllCustomers());

        // Create a new Customer object for the form to be ready for new entries
        model.addAttribute("customer", new Customer());

        return "Main"; // Change this if needed to the name of your actual view
    }

    @GetMapping(path = "/edit")
    public String editCustomer(@RequestParam("id") int id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
        } else {
            model.addAttribute("error", "Customer not found");
        }
        model.addAttribute("customers", customerService.getAllCustomers());
        return "Main";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);

        redirectAttributes.addFlashAttribute("message", "Customer deleted successfully.");

        return "redirect:/";
    }
}
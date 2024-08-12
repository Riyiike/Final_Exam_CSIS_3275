package org.example.lab.Web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lab.Entities.CompoundInterestResult;
import org.example.lab.Entities.Customer;
import org.example.lab.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/")
    public String initialPage(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("customer", new Customer());
        return "Main";
    }

    @GetMapping("/customerForm")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "CustomerForm";
    }

    @PostMapping("/addOrUpdateCustomer")
    public String addOrUpdateCustomer(@ModelAttribute @Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form view with errors
            model.addAttribute("customer", customer);
            return "CustomerForm";
        }

        if (customer.getId() > 0) {
            customerService.updateCustomer(customer); // Updating existing customer
        } else {
            customerService.addCustomer(customer); // Adding new customer
        }

        // Refresh the list of customers to show the latest data in the view
        model.addAttribute("customers", customerService.getAllCustomers());

        // Create a new Customer object for the form to be ready for new entries
        model.addAttribute("customer", new Customer());

        return "redirect:/";
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
        return "CustomerForm";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);

        redirectAttributes.addFlashAttribute("message", "Customer deleted successfully.");

        return "redirect:/";
    }

    @GetMapping("/sales-summary")
    public String showSalesSummary(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "SummaryPage";
    }

    @GetMapping("/compound-interest")
    public String getCompoundInterest(
            @RequestParam("id") long customerId,
            @RequestParam("amount") double amount,
            @RequestParam("yearOfPurchase") int yearOfPurchase,
            @RequestParam("type") String type,
            Model model) {

        // Determine the interest rate based on the customer type
        double interestRate = getInterestRate(type);

        // Validate yearOfPurchase
        if (yearOfPurchase <= 0) {
            model.addAttribute("error", "Invalid year of purchase.");
            return "error";  // Return an error view or handle accordingly
        }

        List<CompoundInterestResult> compoundInterestResults = calculateCompoundInterest(amount, interestRate, yearOfPurchase);

        // Add results to the model to be displayed in the view
        model.addAttribute("compoundInterestResults", compoundInterestResults);

        return "compound-interest";  // Return the view that displays the results
    }

    private double getInterestRate(String type) {
        switch (type.toLowerCase()) {
            case "tech":
                return 0.03;  // Example rate for Tech
            case "individual":
                return 0.05;  // Example rate for Individual
            default:
                return 0.04;  // Default rate
        }
    }

    private List<CompoundInterestResult> calculateCompoundInterest(double initialAmount, double interestRate, int years) {
        List<CompoundInterestResult> results = new ArrayList<>();
        double startingAmount = initialAmount;

        for (int year = 1; year <= years; year++) {
            double interest = startingAmount * interestRate;
            double endingBalance = startingAmount + interest;

            results.add(new CompoundInterestResult(startingAmount, interest, endingBalance));

            startingAmount = endingBalance;  // Use ending balance as the starting amount for the next year
        }

        return results;
    }
}
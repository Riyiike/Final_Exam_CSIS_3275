package org.example.lab.Services;
import org.example.lab.Entities.Customer;
import org.example.lab.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CustomerService {

    // Keep track of used transaction numbers
    private Set<Integer> usedTransactionNumbers = new HashSet<>();


    @Autowired
    private CustomerRepository customerRepository;

    public int generateUniqueTransactionNumber() {
        Random random = new Random();
        int transactionNumber;

        do {
            transactionNumber = random.nextInt(899) + 1; // Generate a number between 1 and 899
        } while (usedTransactionNumbers.contains(transactionNumber));

        usedTransactionNumbers.add(transactionNumber);
        return transactionNumber;
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void addCustomer(Customer customer) {
        if (customer.getTransactionNumber() == 0) {
            customer.setTransactionNumber(generateUniqueTransactionNumber());
        }
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        // If the transaction number is not set, generate a new one
        if (customer.getTransactionNumber() == 0) {
            customer.setTransactionNumber(generateUniqueTransactionNumber());
        }
        customerRepository.save(customer);
    }
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
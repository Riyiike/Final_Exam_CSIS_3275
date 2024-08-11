package org.example.lab.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;


import java.util.Date;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotEmpty(message = "Customer name is required.")
    private String cname;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotNull(message = "Customer type is required.")
    private String type;

    @Min(value = 0, message = "Purchase amount must be positive.")
    private double amount;

    @NotNull(message = "Purchase date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    private int transactionNumber;

    @Min(value = 0, message = "Number of years must be positive.")
    private Integer yearOfPurchase;

    // Default constructor
    public Customer() {}

    public Customer(int id, String cname, String email, String type, double amount, Date purchaseDate, int yearOfPurchase) {
        this.id = id;
        this.cname = cname;
        this.email = email;
        this.type = type;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
        this.transactionNumber = transactionNumber;
        this.yearOfPurchase = yearOfPurchase;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Integer getYearOfPurchase() {
        return yearOfPurchase;
    }

    public void setYearOfPurchase(Integer yearOfPurchase) {
        this.yearOfPurchase = yearOfPurchase;
    }
}
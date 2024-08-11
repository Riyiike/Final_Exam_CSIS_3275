package org.example.lab.Entities;

public class CompoundInterestResult {
    private double startingAmount;
    private double interest;
    private double endingBalance;

    // Constructor
    public CompoundInterestResult(double startingAmount, double interest, double endingBalance) {
        this.startingAmount = startingAmount;
        this.interest = interest;
        this.endingBalance = endingBalance;
    }

    // Getters
    public double getStartingAmount() {
        return startingAmount;
    }

    public double getInterest() {
        return interest;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    // Formatted Getters
    public String getStartingAmountFormatted() {
        return String.format("%.2f", startingAmount);
    }

    public String getInterestFormatted() {
        return String.format("%.2f", interest);
    }

    public String getEndingBalanceFormatted() {
        return String.format("%.2f", endingBalance);
    }
}
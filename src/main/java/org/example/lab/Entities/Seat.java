package org.example.lab.Entities;

public class Seat {
    private String seatNumber; // e.g., "A1", "B2"
    private String reservedBy; // Name of the customer who reserved the seat, null if not reserved

    // Getters and Setters
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }
}

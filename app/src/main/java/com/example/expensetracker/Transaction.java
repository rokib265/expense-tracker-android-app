package com.example.expensetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final double amount;
    private final String category;
    private final String type; // "INCOME" or "EXPENSE"
    private final String date;
    private final String notes;

    public Transaction(double amount, String category, String type, String date, String notes) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }
}

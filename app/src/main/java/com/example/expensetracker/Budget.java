package com.example.expensetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget")
public class Budget {
    @PrimaryKey
    public int id = 1; // logical singleton

    public double amount;

    public Budget(double amount) {
        this.amount = amount;
    }
}

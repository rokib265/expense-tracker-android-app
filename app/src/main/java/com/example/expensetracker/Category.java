package com.example.expensetracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type; // "INCOME" or "EXPENSE"
    private int iconResourceId; // Basic int for drawable resource implementation

    public Category(String name, String type, int iconResourceId) {
        this.name = name;
        this.type = type;
        this.iconResourceId = iconResourceId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getIconResourceId() { return iconResourceId; }
    public void setIconResourceId(int iconResourceId) { this.iconResourceId = iconResourceId; }
}

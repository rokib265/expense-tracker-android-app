package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText amountEditText, dateEditText, notesEditText;
    private android.widget.Spinner categorySpinner;
    private android.widget.RadioGroup typeRadioGroup;
    private ExpenseViewModel expenseViewModel;
    private java.util.List<Category> categories = new java.util.ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountEditText = findViewById(R.id.edit_text_amount);
        categorySpinner = findViewById(R.id.spinner_category); // Need to update layout XML too
        dateEditText = findViewById(R.id.edit_text_date);
        notesEditText = findViewById(R.id.edit_text_notes);
        typeRadioGroup = findViewById(R.id.radio_group_type); // Need to update layout XML too

        expenseViewModel = new androidx.lifecycle.ViewModelProvider(this).get(ExpenseViewModel.class);

        // Setup Category Spinner
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new java.util.ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        expenseViewModel.getAllCategories().observe(this, newCategories -> {
            categories = newCategories;
            java.util.List<String> categoryNames = new java.util.ArrayList<>();
            for (Category c : categories) {
                categoryNames.add(c.getName());
            }
            adapter.clear();
            adapter.addAll(categoryNames);
            adapter.notifyDataSetChanged();
        });

        android.widget.ImageButton addCategoryButton = findViewById(R.id.button_add_category);
        addCategoryButton.setOnClickListener(v -> showAddCategoryDialog());

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(v -> {
            saveTransaction();
        });
    }

    private void showAddCategoryDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add New Category");

        final EditText input = new EditText(this);
        input.setHint("Category Name");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String categoryName = input.getText().toString().trim();
            if (!categoryName.isEmpty()) {
                Category newCategory = new Category(categoryName, "EXPENSE", 0); // Defaulting to EXPENSE for now
                expenseViewModel.insertCategory(newCategory);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveTransaction() {
        String amountStr = amountEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String notes = notesEditText.getText().toString();
        int selectedCategoryPosition = categorySpinner.getSelectedItemPosition();
        
        if (amountStr.trim().isEmpty() || date.trim().isEmpty() || selectedCategoryPosition == -1) {
            android.widget.Toast.makeText(this, "Please insert all details", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String category = categories.get(selectedCategoryPosition).getName();
        
        String type = "EXPENSE"; // Default
        int selectedTypeId = typeRadioGroup.getCheckedRadioButtonId();
        if (selectedTypeId == R.id.radio_income) { // Need id in XML
            type = "INCOME";
        }

        Transaction transaction = new Transaction(amount, category, type, date, notes);
        expenseViewModel.insert(transaction);
        finish();
    }
}

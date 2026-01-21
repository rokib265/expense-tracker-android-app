package com.example.expensetracker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {

    private ExpenseRepository repository;
    private LiveData<List<Transaction>> allTransactions;
    private LiveData<List<Category>> allCategories;
    
    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new ExpenseRepository(application);
        allTransactions = repository.getAllTransactions();
        allCategories = repository.getAllCategories();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    
    public LiveData<Double> getTotalIncome() {
        return repository.getTotalIncome();
    }
    
    public LiveData<Double> getTotalExpense() {
        return repository.getTotalExpense();
    }
    
    public LiveData<List<TransactionDao.CategoryTuple>> getExpenseByCategory() {
        return repository.getExpenseByCategory();
    }
    
    public LiveData<List<TransactionDao.DateTuple>> getLast7DaysExpenses() {
        return repository.getLast7DaysExpenses();
    }

    public void insert(Transaction transaction) {
        repository.insert(transaction);
    }
    
    public void delete(Transaction transaction) {
        repository.delete(transaction);
    }
    
    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }
    
    // Budget
    public LiveData<Budget> getBudget() {
        return repository.getBudget();
    }
    
    public void setBudget(double amount) {
        repository.setBudget(amount);
    }
}

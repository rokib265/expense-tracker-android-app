package com.example.expensetracker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpenseRepository {

    private TransactionDao transactionDao;
    private CategoryDao categoryDao;
    private BudgetDao budgetDao;
    private LiveData<List<Category>> allCategories;

    public ExpenseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        categoryDao = db.categoryDao();
        budgetDao = db.budgetDao();
        allCategories = categoryDao.getAllCategories();
    }

    // Budget Methods
    public LiveData<Budget> getBudget() {
        return budgetDao.getBudget();
    }

    public void setBudget(double amount) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            budgetDao.insert(new Budget(amount));
        });
    }

    // Transaction Methods
    public LiveData<List<Transaction>> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    
    public LiveData<Double> getTotalIncome() {
        return transactionDao.getTotalIncome();
    }

    public LiveData<Double> getTotalExpense() {
        return transactionDao.getTotalExpense();
    }
    
    public LiveData<List<TransactionDao.CategoryTuple>> getExpenseByCategory() {
        return transactionDao.getExpenseByCategory();
    }
    
    public LiveData<List<TransactionDao.DateTuple>> getLast7DaysExpenses() {
        return transactionDao.getLast7DaysExpenses();
    }

    public void insert(Transaction transaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.insert(transaction));
    }
    
    public void delete(Transaction transaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> transactionDao.delete(transaction));
    }

    public void insertCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> categoryDao.insert(category));
    }
}

package com.example.expensetracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'INCOME'")
    LiveData<Double> getTotalIncome();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'EXPENSE'")
    LiveData<Double> getTotalExpense();
    
    @Query("SELECT category, SUM(amount) as total FROM transactions WHERE type = 'EXPENSE' GROUP BY category")
    LiveData<List<CategoryTuple>> getExpenseByCategory();

    @Query("SELECT date, SUM(amount) as total FROM transactions WHERE type = 'EXPENSE' GROUP BY date ORDER BY date ASC LIMIT 7")
    LiveData<List<DateTuple>> getLast7DaysExpenses();

    class DateTuple {
        public String date;
        public double total;
    }

    // Helper class for group by results
    class CategoryTuple {
        public String category;
        public double total;
    }
}

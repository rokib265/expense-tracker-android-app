package com.example.expensetracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private ExpenseViewModel expenseViewModel;

    private Double currentIncome = 0.0;
    private Double currentExpense = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView totalIncome = view.findViewById(R.id.total_income);
        TextView totalExpenses = view.findViewById(R.id.total_expenses);
        TextView totalBalance = view.findViewById(R.id.total_balance);
        PieChart pieChart = view.findViewById(R.id.pie_chart);

        expenseViewModel = new androidx.lifecycle.ViewModelProvider(this).get(ExpenseViewModel.class);

        // Observe Total Income
        expenseViewModel.getTotalIncome().observe(getViewLifecycleOwner(), income -> {
            currentIncome = income != null ? income : 0.0;
            totalIncome.setText(String.format("$%.2f", currentIncome));
            updateBalance(totalBalance);
        });

        // Observe Total Expense
        expenseViewModel.getTotalExpense().observe(getViewLifecycleOwner(), expense -> {
            currentExpense = expense != null ? expense : 0.0;
            totalExpenses.setText(String.format("$%.2f", currentExpense));
            updateBalance(totalBalance);
        });

        // Observe Expense by Category for Pie Chart
        expenseViewModel.getExpenseByCategory().observe(getViewLifecycleOwner(), categoryTuples -> {
            List<PieEntry> entries = new ArrayList<>();
            List<Integer> colors = new ArrayList<>();
            
            if (categoryTuples != null) {
                for (TransactionDao.CategoryTuple tuple : categoryTuples) {
                    entries.add(new PieEntry((float) tuple.total, tuple.category));
                    colors.add(ColorUtils.getColorForCategory(tuple.category));
                }
            }

            if (entries.isEmpty()) {
                pieChart.clear();
                pieChart.setNoDataText("No expenses yet");
                pieChart.invalidate();
                return;
            }

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(colors);
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setValueTextSize(12f);

            PieData pieData = new PieData(dataSet);
            pieChart.setData(pieData);
            
            // UI Styling
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText("Expenses");
            pieChart.setCenterTextSize(16f);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);
            pieChart.setHoleRadius(58f);
            pieChart.getLegend().setEnabled(false);
            
            pieChart.animateY(1000);
            pieChart.invalidate(); // refresh


        });

        // Observe Daily Expenses for Bar Chart
        com.github.mikephil.charting.charts.BarChart barChart = view.findViewById(R.id.bar_chart);
        expenseViewModel.getLast7DaysExpenses().observe(getViewLifecycleOwner(), dateTuples -> {
            List<com.github.mikephil.charting.data.BarEntry> entries = new ArrayList<>();
            List<String> labels = new ArrayList<>();
            
            if (dateTuples != null) {
                int index = 0;
                for (TransactionDao.DateTuple tuple : dateTuples) {
                    entries.add(new com.github.mikephil.charting.data.BarEntry(index, (float)tuple.total));
                    
                    String label = tuple.date;
                    if (label != null && label.length() >= 5) {
                         label = label.substring(5); 
                    }
                    labels.add(label != null ? label : "");
                    index++;
                }
            }
            
            if (entries.isEmpty()) {
                barChart.clear();
                barChart.setNoDataText("No data for chart");
                return;
            }

            com.github.mikephil.charting.data.BarDataSet set = new com.github.mikephil.charting.data.BarDataSet(entries, "Daily Spending");
            set.setColor(android.graphics.Color.parseColor("#45B7D1")); 
            set.setValueTextColor(Color.BLACK);
            set.setValueTextSize(10f);

            com.github.mikephil.charting.data.BarData data = new com.github.mikephil.charting.data.BarData(set);
            data.setBarWidth(0.6f);
            
            barChart.setData(data);
            barChart.setFitBars(true);
            barChart.getDescription().setEnabled(false);
            barChart.animateY(1000);
            
            // X Axis
            com.github.mikephil.charting.components.XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(labels));
            xAxis.setPosition(com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            
            barChart.getAxisLeft().setDrawGridLines(false);
            barChart.getAxisRight().setEnabled(false);
            barChart.invalidate();
        });
     
        // Budget Logic
        android.widget.ProgressBar budgetProgress = view.findViewById(R.id.budget_progress);
        TextView textSpent = view.findViewById(R.id.text_spent_amount);
        TextView textLimit = view.findViewById(R.id.text_budget_limit);
        View btnEditBudget = view.findViewById(R.id.btn_edit_budget);
        View cardBudget = view.findViewById(R.id.card_budget);
        
        expenseViewModel.getBudget().observe(getViewLifecycleOwner(), budget -> {
             double limit = (budget != null) ? budget.amount : 0.0;
             textLimit.setText(String.format("Limit $%.0f", limit));
             
             // Update progress based on total expense
             expenseViewModel.getTotalExpense().observe(getViewLifecycleOwner(), expense -> {
                 double current = (expense != null) ? expense : 0.0;
                 textSpent.setText(String.format("Spent $%.0f", current));
                 
                 int progress = 0;
                 if (limit > 0) {
                     progress = (int) ((current / limit) * 100);
                 }
                 budgetProgress.setProgress(progress);
                 
                 // Change color if over budget
                 if (progress > 100) {
                     budgetProgress.setProgressTintList(android.content.res.ColorStateList.valueOf(Color.RED));
                 } else {
                     budgetProgress.setProgressTintList(null); // Reset to default drawable
                 }
             });
        });
        
        View.OnClickListener editListener = v -> {
            android.widget.EditText input = new android.widget.EditText(getContext());
            input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
            
            new android.app.AlertDialog.Builder(getContext())
                .setTitle("Set Monthly Budget")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String val = input.getText().toString();
                    if (!val.isEmpty()) {
                        expenseViewModel.setBudget(Double.parseDouble(val));
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
        };
        
        btnEditBudget.setOnClickListener(editListener);
        cardBudget.setOnClickListener(editListener);

        return view;
    }

    private void updateBalance(TextView balanceView) {
        double balance = currentIncome - currentExpense;
        balanceView.setText(String.format("$%.2f", balance));
        if (balance >= 0) {
            balanceView.setTextColor(android.graphics.Color.BLUE);
        } else {
            balanceView.setTextColor(android.graphics.Color.RED);
        }
    }
}

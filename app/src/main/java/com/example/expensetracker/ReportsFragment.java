package com.example.expensetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportsFragment extends Fragment {

    private ExpenseViewModel expenseViewModel;
    private CategorySummaryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.reports_recycler_view);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        adapter = new CategorySummaryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel = new androidx.lifecycle.ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getExpenseByCategory().observe(getViewLifecycleOwner(), tuples -> {
            if (tuples != null) {
                adapter.setCategoryTuples(tuples);
            }
        });

        return view;
    }
}

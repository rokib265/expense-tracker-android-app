package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private ExpenseViewModel expenseViewModel;
    private TransactionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.transactions_recycler_view);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext())); // Add LayoutManager

        adapter = new TransactionAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        expenseViewModel = new androidx.lifecycle.ViewModelProvider(this).get(ExpenseViewModel.class);
        expenseViewModel.getAllTransactions().observe(getViewLifecycleOwner(), transactions -> {
            adapter.setTransactions(transactions);
            adapter.notifyDataSetChanged();
        });
        
        adapter.setOnItemLongClickListener(transaction -> {
            new android.app.AlertDialog.Builder(getContext())
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes", (dialog, which) -> expenseViewModel.delete(transaction))
                .setNegativeButton("No", null)
                .show();
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_transaction);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTransactionActivity.class);
            startActivity(intent);
        });

        return view;
    }
}

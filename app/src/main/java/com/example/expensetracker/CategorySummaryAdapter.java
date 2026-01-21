package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategorySummaryAdapter extends RecyclerView.Adapter<CategorySummaryAdapter.ViewHolder> {

    private List<TransactionDao.CategoryTuple> categoryTuples;

    public CategorySummaryAdapter(List<TransactionDao.CategoryTuple> categoryTuples) {
        this.categoryTuples = categoryTuples;
    }
    
    public void setCategoryTuples(List<TransactionDao.CategoryTuple> categoryTuples) {
        this.categoryTuples = categoryTuples;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionDao.CategoryTuple tuple = categoryTuples.get(position);
        holder.text1.setText(tuple.category);
        holder.text2.setText(String.format("$%.2f", tuple.total));
    }

    @Override
    public int getItemCount() {
        return categoryTuples.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}

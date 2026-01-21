package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactions;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Transaction transaction);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.amount.setText(String.format("$%.2f", transaction.getAmount()));
        holder.category.setText(transaction.getCategory());
        holder.date.setText(transaction.getDate());
        holder.notes.setText(transaction.getNotes());
        
        // Icon logic
        String catName = transaction.getCategory();
        if (catName != null && !catName.isEmpty()) {
            holder.icon.setText(catName.substring(0, 1).toUpperCase());
            
            android.graphics.drawable.GradientDrawable bg = (android.graphics.drawable.GradientDrawable) holder.icon.getBackground();
            if (bg != null) {
                bg.setColor(ColorUtils.getColorForCategory(catName));
            }
        }
        
        if ("INCOME".equals(transaction.getType())) {
            holder.amount.setTextColor(android.graphics.Color.parseColor("#00C853")); // income_green
        } else {
            holder.amount.setTextColor(android.graphics.Color.parseColor("#D50000")); // expense_red
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(transaction);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView amount, category, date, notes, icon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.transaction_amount);
            category = itemView.findViewById(R.id.transaction_category);
            date = itemView.findViewById(R.id.transaction_date);
            notes = itemView.findViewById(R.id.transaction_notes);
            icon = itemView.findViewById(R.id.category_icon);
        }
    }
}

package com.fake.piggyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fake.piggyapp.databinding.ItemTransactionBinding
import com.fake.piggyapp.database.TransactionEntity

class TransactionAdapter(private var transactions: List<TransactionEntity>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.binding.tvTransactionType.text = transaction.type
        holder.binding.tvTransactionAmount.text = transaction.amount.toString()
        holder.binding.tvTransactionDate.text = transaction.date
        holder.binding.tvTransactionCategory.text = transaction.category
        holder.binding.tvTransactionDescription.text = transaction.description
        holder.binding.tvTransactionRecurrence.text = transaction.recurringType
    }

    fun updateBudgets(newBudgets: List<TransactionEntity>) {
        this.transactions = newBudgets
        notifyDataSetChanged()
    }
}
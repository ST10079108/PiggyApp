package com.fake.piggyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fake.piggyapp.databinding.ItemBudgetBinding
import com.fake.piggyapp.database.BudgetEntity

class BudgetAdapter(private var budgets: List<BudgetEntity>) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    inner class BudgetViewHolder(val binding: ItemBudgetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int = budgets.size

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgets[position]
        holder.binding.tvBudgetName.text = budget.name
        holder.binding.tvBudgetCategory.text = budget.category
        holder.binding.tvBudgetMinimumAmount.text = budget.min.toString()
        holder.binding.tvBudgetMaximumAmount.text = budget.max.toString()
    }

    fun updateBudgets(newBudgets: List<BudgetEntity>) {
        this.budgets = newBudgets
        notifyDataSetChanged()
    }
}
package com.example.nutech_ewallet_app.ui.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutech_ewallet_app.data.entity.Transaction
import com.example.nutech_ewallet_app.databinding.ItemTransactionBinding
import com.example.nutech_ewallet_app.utils.Utils
import java.text.NumberFormat
import java.util.*

class TransactionAdapter(private val transactions: List<Transaction?>) :
    RecyclerView.Adapter<TransactionAdapter.ListViewHolder>() {

    inner class ListViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = transactions.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.binding.apply {
            tvTitle.text = transactions[position]!!.transactionType
            tvDateTime.text = transactions[position]!!.transactionTime
            tvAmount.text = Utils.toIdrCurrency(transactions[position]?.amount!!)
        }
    }
}
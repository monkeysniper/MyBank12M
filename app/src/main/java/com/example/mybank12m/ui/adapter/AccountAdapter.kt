package com.example.mybank12m.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybank12m.data.model.Account
import com.example.mybank12m.databinding.ItemBinding

class AccountAdapter(
    val onEdit:(Account)->Unit,
    val onDelete: (String)->Unit
) :RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private val items = mutableListOf<Account>()

    fun submitList(data: List<Account>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountViewHolder {
        return AccountViewHolder(
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

   inner class AccountViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {

             binding.tvName.text = account.name
             binding.tvBalance.text = account.balance.toString()
            binding.btnChange.setOnClickListener {
                onEdit(account)
            }
            binding.btnDelete.setOnClickListener {
                account.id?.let{onDelete(it)}
            }
        }
    }
}
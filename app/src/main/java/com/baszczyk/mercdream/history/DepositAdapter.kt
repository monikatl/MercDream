package com.baszczyk.mercdream.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercdream.database.enities.Deposit
import com.baszczyk.mercdream.databinding.DepositItemViewBinding

class DepositAdapter : ListAdapter<Deposit, DepositAdapter.ViewHolder>(DepositDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: DepositItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Deposit) {
            binding.deposit = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DepositItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class DepositDiffCallback : DiffUtil.ItemCallback<Deposit>() {

    override fun areItemsTheSame(oldItem: Deposit, newItem: Deposit): Boolean {
        return oldItem.depositId == newItem.depositId
    }


    override fun areContentsTheSame(oldItem: Deposit, newItem: Deposit): Boolean {
        return oldItem == newItem
    }


}
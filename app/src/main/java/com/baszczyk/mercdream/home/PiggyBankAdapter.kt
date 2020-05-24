package com.baszczyk.mercdream.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercdream.database.enities.PiggyBank
import com.baszczyk.mercdream.databinding.FragmentPiggyBankBinding
import com.baszczyk.mercdream.databinding.ItemViewBinding

class PiggyBankAdapter(val clickListener: PiggyBankListener): ListAdapter<PiggyBank, PiggyBankAdapter.ViewHolder>(PiggyBankDiffCallback()){

//    var piggyId = 0L
//
////    var data = listOf<PiggyBank>()
////        set(value) {
////            field = value
////            notifyDataSetChanged()
////        }
////
//   override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        piggyId = item.piggyId
//        val res = holder.itemView.context.resources
//        holder.surname.text = item.piggyName
//        holder.amount.text = item.actualAmount.toString()

        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class PiggyBankDiffCallback : DiffUtil.ItemCallback<PiggyBank>() {

        override fun areItemsTheSame(oldItem: PiggyBank, newItem: PiggyBank): Boolean {
            return oldItem.piggyId == newItem.piggyId
        }

        override fun areContentsTheSame(oldItem: PiggyBank, newItem: PiggyBank): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder private constructor (val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: PiggyBank, clickListener: PiggyBankListener){
            binding.piggy = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
//        val surname: TextView = itemView.findViewById(R.id.surname_mercedes)
//        val amount: TextView = itemView.findViewById(R.id.actual_amount)
    }


}

class PiggyBankListener(val clickListener: (id: Long) -> Unit){
    fun onClick(piggy: PiggyBank) = clickListener(piggy.piggyId)

}
package com.baszczyk.mercdream.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercdream.R
import com.baszczyk.mercdream.TextItemViewHolder
import com.baszczyk.mercdream.database.PiggyBank
import kotlinx.android.synthetic.main.item_view.view.*

class PiggyBankAdapter: RecyclerView.Adapter<TextItemViewHolder>(){

    var data = listOf<PiggyBank>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.piggyId.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

}
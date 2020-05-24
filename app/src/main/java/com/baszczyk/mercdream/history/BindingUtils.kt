package com.baszczyk.mercdream.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.baszczyk.mercdream.database.enities.Deposit


@BindingAdapter("date")
fun TextView.setDate(item: Deposit?) {
    item?.let {
        text = item.date
    }
}


@BindingAdapter("amount")
fun TextView.setAmount(item: Deposit?) {
    item?.let {
        text = item.amount.toString()
    }
}

@BindingAdapter("mercedes_name")
fun TextView.setMercedesName(item: Deposit?) {
    item?.let {
        text = item.piggyName
    }
}
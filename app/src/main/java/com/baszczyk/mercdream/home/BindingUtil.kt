package com.baszczyk.mercdream.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.baszczyk.mercdream.database.enities.PiggyBank


@BindingAdapter("piggyName")
fun TextView.setPiggyName(item: PiggyBank?) {
    item?.let {
        text = item.piggyName
    }
}


@BindingAdapter("piggyAmount")
fun TextView.setPiggyAmount(item: PiggyBank?) {
    item?.let {
        text = item.actualAmount.toString()
    }
}

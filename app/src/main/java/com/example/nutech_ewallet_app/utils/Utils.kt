package com.example.nutech_ewallet_app.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.NumberFormat
import java.util.*

object Utils {
    fun toIdrCurrency(p0: Int): String {
        val locale = Locale("in", "ID")
        return NumberFormat.getCurrencyInstance(locale).format(p0).substringBefore(",")
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

package com.dixa.dixagitpreview.util

import android.content.Context
import com.dixa.dixagitpreview.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object TimeUtil {

    @JvmStatic
    fun getCurrentMillis(): Long {
        return System.currentTimeMillis()
    }

    @JvmStatic
    fun daysDifferent(date: Date, context: Context): String {
        val diff: Long = date.time - getCurrentMillis()
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        var days = hours / 24
        var beautyDate = ""

        val calendar = GregorianCalendar.getInstance()
        calendar.time = date
        val currentCalendar = GregorianCalendar.getInstance()


        beautyDate = if (days == 0L) {
            context.getString(R.string.today)
        } else {
            when {
                days == -1L -> context.getString(R.string.yesterday)
                days <= -2L && days >= -31L -> context.getString(R.string.x_days_ago, abs(days).toString())
                days <= -31L -> {
                    val dayOfMonth = SimpleDateFormat("dd", Locale.US).format(calendar.time)
                    val monthName = SimpleDateFormat("MMM", Locale.US).format(calendar.time)
                    val year = SimpleDateFormat("yyyy", Locale.US).format(calendar.time)
                    val currentYear = SimpleDateFormat("yyyy", Locale.US).format(currentCalendar.time)

                    if (currentYear == year) {
                        "$monthName $dayOfMonth"
                    } else {
                        "$monthName $dayOfMonth, $year"
                    }
                }
                else -> ""
            }
        }
        return beautyDate
    }

}
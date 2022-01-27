package com.chibi.waie.common

import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtils class provides util method to get the result of converted format for
 * current Day, Month, Year
 */
object DateUtils {

    fun getCurrentDay(): String {
        val outPutDateFormat = SimpleDateFormat("dd", Locale.getDefault())
        return outPutDateFormat.format(Calendar.getInstance().time)
    }

    fun getCurrentMonth(): String {
        val outPutDateFormat = SimpleDateFormat("MM", Locale.getDefault())
        return outPutDateFormat.format(Calendar.getInstance().time)
    }

    fun getCurrentYear(): String {
        val outPutDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        return outPutDateFormat.format(Calendar.getInstance().time)
    }
}
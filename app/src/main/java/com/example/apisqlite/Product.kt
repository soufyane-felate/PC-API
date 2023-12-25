package com.example.apisqlite

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

data class Product(
    val nom: String,
    val systeme_exploitation: String,
    val date_fin_os: LocalDate,
    val prix: String,
    val image: String
) {
    data class EndOfSupport(val days: Long, val months: Long, val years: Long)

    @SuppressLint("NewApi")
    fun differenceInYears(): EndOfSupport {
        val difference = ChronoUnit.DAYS.between(LocalDate.now(), date_fin_os)
        val days = difference % 30
        val months = (difference % 365) / 30
        val years = difference / 365

        return EndOfSupport(days, months, years)
    }
}

object ProductUtils {
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDateString(dateString: String): LocalDate {
        val frenchMonthNames = listOf(
            "janvier", "février", "mars", "avril", "mai", "juin",
            "juillet", "août", "septembre", "octobre", "novembre", "décembre"
        )

        val parts = dateString.split(" ")
        val day = parts[0].toInt()
        val month = frenchMonthNames.indexOf(parts[1]) + 1
        val year = parts[2].toInt()

        return LocalDate.of(year, month, day)
    }
}


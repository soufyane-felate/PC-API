package com.example.apisqlite
/*
import java.time.LocalDate
import java.time.Period
import java.util.Date


data class Product(
    val nom: String,
    val systeme_exploitation: String,
    val date_fin_os: String,
   // val date_fin_os1: Date,

    val prix: String,
    val image: String,
) {
    override fun toString(): String {
        return "Product(nom='$nom', systeme_exploitation='$systeme_exploitation', date_fin_os='$date_fin_os', prix='$prix', image='$image')"
    }

       /* fun deference(): Int {
            return Period.between(date_fin_os1, LocalDate.now()).years
        }

        */


}



 */
import android.annotation.SuppressLint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


data class Product(
    val nom: String,
    val systeme_exploitation: String,
    val date_fin_os: LocalDate,
    val prix: String,
    val image: String
)
 {
    @SuppressLint("NewApi")
    fun differenceInYears(): Int {
        return LocalDate.now().until(date_fin_os).years
    }
}



object ProductUtils {
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








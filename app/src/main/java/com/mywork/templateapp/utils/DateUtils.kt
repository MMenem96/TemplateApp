package com.mywork.templateapp.utils


import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        private val TAG: String = "DateUtils"

        // dates from server look like this: "2019-07-23T03:28:01.406944Z"
        fun convertServerStringDateToLong(sd: String): Long {
            var stringDate = sd.removeRange(sd.indexOf("T") until sd.length)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val time = sdf.parse(stringDate).time
                return time
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun convertServerStringDateToBirthDate(serverDate: String?): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            var convertedDate = ""
            try {
                val dateObj = sdf.parse(serverDate)
                convertedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dateObj)
                return convertedDate
            } catch (e: ParseException) {
                e.printStackTrace()
                return serverDate ?: ""
            }
        }

        fun convertLongToStringDate(longDate: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val date = sdf.format(Date(longDate))
                return date
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun convertLongToStringDateTime(longDate: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
            try {
                val date = sdf.format(Date(longDate))
                return date
            } catch (e: Exception) {
                throw Exception(e)
            }
        }

        fun convertServerStringToCalendar(birthDate: String): Calendar? {
            try {
                val cal: Calendar = Calendar.getInstance();
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                cal.setTime(sdf.parse(birthDate))
                return cal
            } catch (e: Exception) {
                return null

            }

        }
    }


}
package com.example.adammb.jadwalbalbalan.util

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.example.adammb.jadwalbalbalan.model.event.Event
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun formatDateTime(date: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        return formatter.parse(date)
    }

    fun formatDate(date: String?): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val formatDate = SimpleDateFormat("E, dd MMMM yyyy", Locale.getDefault())

        return formatDate.format(formatter.parse(date))
    }

    fun formatTime(date: String?): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val formatTime = SimpleDateFormat("HH:mm", Locale.getDefault())

        return formatTime.format(formatter.parse(date))
    }

    fun calendarIntent(context: Context?, event: Event) {
        val title = event.teamHomeName + " VS " + event.teamAwayName

        // Begin time
        val dateTime = DateUtil.formatDateTime(event.date + " " + event.time)
        val calendar = Calendar.getInstance()
        calendar.time = dateTime
        val beginTime = calendar.timeInMillis

        // End time plus 1.45 hours, because soccer 45*2+15
        calendar.add(Calendar.HOUR, 1)
        calendar.add(Calendar.MINUTE, 45)
        val endTime = calendar.timeInMillis

        val intent = Intent(Intent.ACTION_INSERT)
        intent.setData(CalendarContract.Events.CONTENT_URI)
        intent.putExtra(CalendarContract.Events.TITLE, title)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
        intent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS, true)
        intent.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM)
        intent.putExtra(CalendarContract.Reminders.DURATION, 60)
        intent.putExtra(CalendarContract.Reminders.MINUTES, 60)
        intent.putExtra(CalendarContract.Events.ALL_DAY, false)
        context?.startActivity(intent)
    }
}
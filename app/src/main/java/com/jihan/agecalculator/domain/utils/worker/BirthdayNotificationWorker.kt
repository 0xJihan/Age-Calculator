package com.jihan.agecalculator.domain.utils.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jihan.agecalculator.R
import com.jihan.agecalculator.domain.room.AgeEntity
import com.jihan.agecalculator.domain.room.AppDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltWorker
class BirthdayNotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,

    ) : Worker(context, workerParams) {

    override fun doWork(): Result {

        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return Result.success()
        }else{
        doBackgroundWork(context)
        return Result.success()
        }
    }


}


private fun doBackgroundWork(context: Context) {

    val ageDao = AppDatabase.getInstance(context).ageDao()


    CoroutineScope(Dispatchers.IO).launch {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        val ageList = ageDao.getAllAge()

        val birthdayUsers = ageList.filter { user ->
            val birthday = user.start
            (birthday.month == today.month && birthday.dayOfMonth == today.dayOfMonth) || (birthday.month == tomorrow.month && birthday.dayOfMonth == tomorrow.dayOfMonth)
        }
        birthdayUsers.forEach {
            showNotification(context, it, today)
        }

        Log.d("jihan khan ageList", ageList.toString())
    }


}

private fun showNotification(context: Context, user: AgeEntity, today: LocalDate) {
    val channelId = "BIRTHDAY_NOTIFICATION"
    val notificationId = user.id ?: 0

    val channel = NotificationChannel(
        channelId, "Birthday Notifications", NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "Notifications for upcoming birthdays"
    }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    val title = if (user.start.dayOfMonth == today.dayOfMonth) {
        "Today is ${user.name}'s birthday"
    } else {
        "Tomorrow is ${user.name}'s birthday"
    }

    val builder = NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.profile)
        .setContentTitle(title).setContentText("Don't forget to wish them a happy birthday!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("jihan khan", "Permission not granted to show notifications")
            return
        }
        notify(notificationId, builder.build())
    }
}


fun scheduleBirthdayCheck(context: Context) {
    val workManager = WorkManager.getInstance(context)

    // Schedule the morning task at 8:00 AM
    val morningDelay = calculateDelay(8)
    val morningWorkRequest =
        OneTimeWorkRequestBuilder<BirthdayNotificationWorker>().setInitialDelay(
                morningDelay,
                TimeUnit.MILLISECONDS
            ).build()

    // Schedule the evening task at 8:00 PM
    val eveningDelay = calculateDelay(20)
    val eveningWorkRequest =
        OneTimeWorkRequestBuilder<BirthdayNotificationWorker>().setInitialDelay(
                eveningDelay,
                TimeUnit.MILLISECONDS
            ).build()

    // Enqueue both tasks as unique workers
    workManager.enqueueUniqueWork(
        "MorningBirthdayCheck", ExistingWorkPolicy.REPLACE, morningWorkRequest
    )
    workManager.enqueueUniqueWork(
        "EveningBirthdayCheck", ExistingWorkPolicy.REPLACE, eveningWorkRequest
    )
}

private fun calculateDelay(targetHour: Int): Long {
    val now = Calendar.getInstance()
    val targetTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, targetHour)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (before(now)) {
            // If the target time has already passed, schedule for the next day
            add(Calendar.DAY_OF_YEAR, 1)
        }
    }
    return targetTime.timeInMillis - now.timeInMillis
}



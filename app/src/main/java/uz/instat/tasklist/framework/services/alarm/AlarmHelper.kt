package uz.instat.tasklist.framework.services.alarm

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import uz.instat.tasklist.busines.local.TaskLocal


class AlarmHelper constructor(
    private val context: Context
) {
    private var mAlarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarm(task: TaskLocal) {
        val bundle = Bundle()
        bundle.putParcelable(TASK_NAME, task)
        val intent = Intent(context, AlarmReceiver::class.java)
            .putExtra(TASK_BUNDLE, bundle)

        val pendingIntent1 = PendingIntent.getBroadcast(
            context, (task.time - task.alarmTime).toInt(),
            intent,   PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            mAlarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                task.time - task.alarmTime,
                pendingIntent1
            )
        else
            mAlarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                task.time - task.alarmTime,
                pendingIntent1
            )


        val pendingIntent = PendingIntent.getBroadcast(
            context, task.time.toInt(),
            intent,   PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            mAlarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                task.time,
                pendingIntent
            )
        else
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, task.time, pendingIntent)

    }

    fun removeNotification(id: Long, context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id.toInt())
    }

    fun removeAlarm(taskTimeStamp: Long) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, taskTimeStamp.toInt(),
            intent,   PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        mAlarmManager.cancel(pendingIntent)
    }
}

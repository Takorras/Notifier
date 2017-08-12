package info.kotako.Taaker.Service

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.NotificationCompat
import info.kotako.Taaker.R

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notification: Notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("ほげほげ")
                .build()

        (context!!.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(0, notification)
    }
}

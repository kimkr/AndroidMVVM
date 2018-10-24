package com.dumi.svq_ver10.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.dumi.svq_ver10.R
import com.dumi.svq_ver10.persistence.repository.AuthRepository
import com.dumi.svq_ver10.persistence.repository.TaskRepository
import com.dumi.svq_ver10.ui.main.MainActivity
import com.dumi.svq_ver10.ui.popup.PopupActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import javax.inject.Inject

class FirebaseMsgService : FirebaseMessagingService() {

    @field:[Inject]
    lateinit var taskRepository: TaskRepository
    @field:[Inject]
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onNewToken(token: String?) {
        val token = FirebaseInstanceId.getInstance().token
        authRepository.setToken(token!!)
    }

    override fun onMessageReceived(message: RemoteMessage?) {
        var title = message!!.notification!!.title
        var content = message!!.data["ctk_queMsg"]
        Log.d("FirebaseMsgService", "onMessageReceived $title $content")
        showPopup()
        sendNotification(title, content)
    }

    private fun showPopup() {
        val intent = Intent(this, PopupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun sendNotification(title: String?, content: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        val contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this)
                .setVibrate(longArrayOf(0, 100, 100, 100, 100, 100))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
        notificationManager.notify(0, builder.build())
        notificationManager.cancel(0)
    }
}
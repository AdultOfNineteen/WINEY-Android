package com.teamwiney.winey

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class WineyFirebaseMessagingService : FirebaseMessagingService() {


    private val TAG = "fcm"
    private val CHANNEL_ID = "notification_remote_channel"

    private lateinit var notificationManager: NotificationManager

    // Token 생성
    override fun onNewToken(token: String) {
        Log.d(TAG, "new Token: $token")
    }

    // foreground 메세지 수신시 동작 설정
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        // 받은 remoteMessage의 값 출력해보기. 데이터메세지 / 알림메세지
        val messageData = "${remoteMessage.notification?.let {
            "${it.title} ${it.body}"
        }}"
        Log.d(TAG, "Message data : $messageData")
        Log.d(TAG, "Message noti : ${remoteMessage.notification?.imageUrl}")

        if (messageData.isNotEmpty()) {
            //알림 생성
            sendNotification(remoteMessage)
        } else {
            Log.e(TAG, "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel =
                NotificationChannel(CHANNEL_ID, "notification_channel", importance)

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 메세지 알림 생성
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val pendingIntent = getFcmPendingIntent(this)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(remoteMessage.notification?.title) // 제목
            .setContentText(remoteMessage.notification?.body) // 메시지 내용
            .setAutoCancel(true) // 알람클릭시 삭제여부
            .setDefaults(Notification.DEFAULT_ALL) // 진동, 소리, 불빛 설정
            .setPriority(NotificationCompat.PRIORITY_HIGH) // 헤드업 알림
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        createNotificationChannel()
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun getFcmPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}
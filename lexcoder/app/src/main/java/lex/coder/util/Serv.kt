package lex.coder.util

import android.app.Service
import android.content.Intent
import android.os.IBinder

class Serv : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(111, App.news)
        return START_STICKY
    }
}
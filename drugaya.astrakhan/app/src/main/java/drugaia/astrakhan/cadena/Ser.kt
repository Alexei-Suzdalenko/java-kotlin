package drugaia.astrakhan.cadena

import android.app.Service
import android.content.Intent
import android.os.IBinder
import drugaia.astrakhan.cadena.App.Companion.playExoplayer
import drugaia.astrakhan.cadena.App.Companion.url


class Ser: Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(111, App.news)
        playExoplayer(url, this)
        return START_STICKY
    }



}
package radio

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import radio.App.Companion.nameStationShow
import radio.App.Companion.playExoplayer
import radio.App.Companion.url

class Service_russkoe_radio: Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(111, App.news)
        Toast.makeText(this, nameStationShow, Toast.LENGTH_LONG).show()
        playExoplayer(url, this)
        return START_STICKY
    }



}
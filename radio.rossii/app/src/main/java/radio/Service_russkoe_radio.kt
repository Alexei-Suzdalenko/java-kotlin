package radio
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import radiorossii.radio.rossii.MainActivity
import radiorossii.radio.rossii.R
import radio.App.Companion.channel_id
import radio.App.Companion.nameStationShow
import radio.App.Companion.playExoplayer
import radio.App.Companion.url

class Service_russkoe_radio: Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
     //  val aplication = getString(R.string.app_name)
     //  val ads = getString(R.string.ads)
     //  val notIntent = Intent(this, MainActivity::class.java)
     //      notIntent.putExtra("stop", "stop")
     //  val pendIntent = PendingIntent.getActivity(this, 123, notIntent, 0)
     //  val news = NotificationCompat.Builder(this, channel_id)
     //      .setContentTitle(aplication)
     //      .setContentText(ads)
     //      .setSmallIcon(R.mipmap.ic_launcher)
     //      .setContentIntent(pendIntent)
     //      .build()
        startForeground(111, App.news)
        Toast.makeText(this, nameStationShow, Toast.LENGTH_LONG).show()
        playExoplayer(url, this)
        return START_STICKY
    }



}
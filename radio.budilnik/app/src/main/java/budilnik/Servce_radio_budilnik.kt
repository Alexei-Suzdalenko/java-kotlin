package budilnik
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.core.app.NotificationCompat
import radio.budilnik.Off_sound
import radio.budilnik.R
import radio.budilnik.Index

class Servce_radio_budilnik : Service() {
    lateinit var sharedPreferences: SharedPreferences
    var dataTimeString = ""
    var title          = ""
    var name_radio     = ""
    var offSound       = ""

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("radio", Context.MODE_PRIVATE)
        dataTimeString    = sharedPreferences.getString("dataTimeString", "Error").toString()
        val use           = resources.getString(R.string.installlition)
        name_radio        = sharedPreferences.getString("name_radio", "none").toString()
        title             = "$use $dataTimeString"
        offSound          = getString(R.string.offSound)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, Off_sound::class.java)
        val goToIndexPrepare   = Intent(this, Index::class.java)
        val pendingIntent      = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val goToIndex          = PendingIntent.getActivity(this, 0,goToIndexPrepare,0)
        val notification       = NotificationCompat.Builder(this, App.channel_id)
                                 .setContentTitle(title)
                                 .setContentText(name_radio)
                                 .setSmallIcon(R.mipmap.ic_launcher)
                                 .addAction(0, offSound, pendingIntent)
                                 .setContentIntent(goToIndex)
                                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                                 .build()
        startForeground(123, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? { return null }
}
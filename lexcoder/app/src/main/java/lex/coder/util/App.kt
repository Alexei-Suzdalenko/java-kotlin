package lex.coder.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import lex.coder.MainActivity
import lex.coder.R

class App : Application() {

    companion object{
        lateinit var news: Notification
        lateinit var myRef: DatabaseReference
        lateinit var database : FirebaseDatabase
        const val channel_id = "cadena-dial"
        const val name = "Cadena Dial Radio"

        fun playSound(c: Context){
            val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(c, notification)
            r.play()
        }
    }

    override fun onCreate() {
        super.onCreate()
        database = Firebase.database
        myRef = database.getReference("chat")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serChannel = NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serChannel)
        }
        val aplication = getString(R.string.app_name)
        val ads = getString(R.string.app_name)
        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.putExtra("stop", "stop")
        val pendIntent = PendingIntent.getActivity(this, 123, notIntent, 0)
        news = NotificationCompat.Builder(this, channel_id)
            .setContentTitle(aplication)
            .setContentText(ads)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendIntent)
            .build()
    }
}
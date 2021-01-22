package read.novosti.rossii
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import russkoe.radio_rossii.R
import russkoe.radio_rossii.Radio_rossii
import russkoe.radio_rossii.ui.main.Novosti_rossii_dataClass
import java.lang.Exception
import java.net.URL

class App : Application() {
    companion object{
        lateinit var news: Notification
        lateinit var todayList:      MutableList<Novosti_rossii_dataClass>
        lateinit var glavnoe:        MutableList<Novosti_rossii_dataClass>
        lateinit var lentaList:      MutableList<Novosti_rossii_dataClass>
        lateinit var blogList:       MutableList<Novosti_rossii_dataClass>
        lateinit var mnenieList:     MutableList<Novosti_rossii_dataClass>
        lateinit var statiiList:     MutableList<Novosti_rossii_dataClass>
        lateinit var politicaList:   MutableList<Novosti_rossii_dataClass>
        lateinit var objestvoList:   MutableList<Novosti_rossii_dataClass>
        lateinit var youtubeList:    MutableList<Novosti_rossii_dataClass>

        var nameStationShow = "Русское Радио"
        var url = "https://rusradio.hostingradio.ru/rusradio128.mp3"
        var playImageVisible = true
        const val channel_id = "cadena-dial"
        const val name = "Cadena Dial Radio"
        lateinit var player : SimpleExoPlayer

        fun playExoplayer(s:String, context: Context){
            val content : ProgressiveMediaSource = ProgressiveMediaSource.Factory(
                DefaultDataSourceFactory(context , "Mozilla")
            ).createMediaSource(Uri.parse(s))
            player.prepare(content)
            player.playWhenReady = true
        }

        fun stopRadio(){
            if(player.isPlaying){
                player.stop()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        todayList    = arrayListOf()
        glavnoe      = arrayListOf()
        lentaList    = arrayListOf()
        blogList     = arrayListOf()
        mnenieList   = arrayListOf()
        statiiList   = arrayListOf()
        politicaList = arrayListOf()
        objestvoList = arrayListOf()
        youtubeList  = arrayListOf()



        player = ExoPlayerFactory.newSimpleInstance(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serChannel = NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serChannel)
        }

        val aplication = getString(R.string.app_name)
        val ads = getString(R.string.ads)
        val notIntent = Intent(this, Radio_rossii::class.java)
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
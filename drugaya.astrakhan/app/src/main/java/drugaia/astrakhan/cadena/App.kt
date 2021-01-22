package drugaia.astrakhan.cadena
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
import drugaia.astrakhan.DrugaiaAstrajan
import drugaia.astrakhan.R
import java.lang.Exception
import java.net.URL

class App : Application() {
    companion object{
        lateinit var news: Notification
        var nameStationShow = "Los 40"
        var url = "http://89.179.72.53:8070/live"
        var playImageVisible = true
        const val channel_id = "cadena-dial"
        const val name = "Cadena Dial Radio"
        lateinit var player : SimpleExoPlayer
        var permission = "not"

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


    override fun onCreate(){
        super.onCreate()
        val tread = Thread{
            try{
                permission = URL("https://diseno-desarrollo-web-app-cantabria.github.io/drugaia_astrajan/permission.txt").readText()
            } catch (e: Exception){}
            try{
                url = URL("https://diseno-desarrollo-web-app-cantabria.github.io/drugaia_astrajan/only.txt").readText()
            } catch (e: Exception){}
        }
        tread.start()


        player = ExoPlayerFactory.newSimpleInstance(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serChannel = NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serChannel)
        }

        val aplication = getString(R.string.app_name)
        val ads = getString(R.string.ads)
        val notIntent = Intent(this, DrugaiaAstrajan::class.java)
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
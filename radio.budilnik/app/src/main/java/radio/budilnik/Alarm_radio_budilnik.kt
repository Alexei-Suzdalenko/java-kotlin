package radio.budilnik
import android.app.AlarmManager
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import budilnik.App
import budilnik.Receiver_radio_budilnik
import kotlinx.android.synthetic.main.alarm_radio_budilnik.*
class Alarm_radio_budilnik : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_radio_budilnik)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else { this.window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON) }


        sharedPreferences = getSharedPreferences("radio", Context.MODE_PRIVATE)
        alarmManager      = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val dataTimeLong  = sharedPreferences.getLong("dataTimeLong", 1)
        val next = dataTimeLong + 86400000
        val edit = sharedPreferences.edit()
            edit.putLong("dataTimeLong",  next); edit.apply()
        val intent        = Intent(applicationContext, Receiver_radio_budilnik::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next, pendingIntent)
        } else { alarmManager.setExact(AlarmManager.RTC_WAKEUP, next, pendingIntent) }


        webView2.webViewClient = WebViewClient()
        val data : Uri? = intent.data
        if(data == null){
            val whatHappens   = sharedPreferences.getString("key", "none").toString()
            if ( whatHappens == "spain") webView2.loadUrl("https://alexander-vishnevsky.web.app/")
            else webView2.loadUrl("https://grani-ru-org.appspot.com/")
        } else { webView2.loadUrl(data?.toString()) }



        val puth    = sharedPreferences.getString("url_radio", "http://89.179.72.53:8070/live").toString()
        if (isInternetAvailable(this)){
             App.play(this, puth)
        } else App.mpPlay(this)


        off_r.setOnClickListener{
            if (App.player.isPlaying) App.player.stop()
            if (App.mp.isPlaying)     App.mp.stop()
            finish()
            startActivity(Intent(this, Index::class.java))
        }


    }


    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }


}









































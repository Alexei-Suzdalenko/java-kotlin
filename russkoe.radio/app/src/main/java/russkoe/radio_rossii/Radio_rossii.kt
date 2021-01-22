package russkoe.radio_rossii
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.radio_rossii.*
import org.json.JSONObject
import read.novosti.rossii.App
import read.novosti.rossii.App.Companion.nameStationShow
import read.novosti.rossii.App.Companion.playImageVisible
import read.novosti.rossii.App.Companion.url
import russkoe.radio_rossii.ui.main.CustomAdapter
import russkoe.radio_rossii.ui.main.Service_russkoe_radio
import java.lang.Exception
import java.net.URL
class Radio_rossii : AppCompatActivity() {
    lateinit var nameStations: Array<String>
    lateinit var urlStations:  Array<String>
    lateinit var mAdView : AdView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.radio_rossii)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, play.toString())

        if(playImageVisible){
            stop.visibility = View.GONE
            play.visibility = View.VISIBLE
        } else {
            play.visibility = View.GONE
            stop.visibility = View.VISIBLE
        }
        play.setOnClickListener {
            playImageVisible = false
            play.visibility = View.GONE
            stop.visibility = View.VISIBLE
            ContextCompat.startForegroundService(this, Intent(this, Service_russkoe_radio::class.java))
        }
        stop.setOnClickListener {
            playImageVisible = true
            stop.visibility = View.GONE
            play.visibility = View.VISIBLE
            stopService(Intent(this, Service_russkoe_radio::class.java))
            App.stopRadio()
        }
        share.setOnClickListener{
            val s     = Intent()
            s.action = Intent.ACTION_SEND
            s.type   = "text/plain"
            s.putExtra(Intent.EXTRA_TEXT, "Русское Радио, Русские новости \n https://play.google.com/store/apps/details?id=russkoe.radio_rossii")
            startActivity(Intent.createChooser(s, ""))
        }
        coment.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=russkoe.radio_rossii")))
        }

        news.setOnClickListener{
            App.todayList.clear()
            App.glavnoe.clear()
            App.lentaList.clear()
            App.blogList.clear()
            App.mnenieList.clear()
            App.statiiList.clear()
            App.politicaList.clear()
            App.objestvoList.clear()
            App.youtubeList.clear()
            startActivity(Intent(this, Novosti_rossii::class.java))
        }


        val readRadioStation = Thread{
            try{
                val response     = URL("https://diseno-desarrollo-web-app-cantabria.github.io/rus-radio/rus_radio.js").readText()
                val res          = JSONObject(response)
                val name         = res.getJSONArray("name")
                nameStations = Array(name.length()){name.getString(it)}
                val url          = res.getJSONArray("uri")
                urlStations  = Array(url.length()){url.getString(it)}
                relativeLayoutGeneral.post{
                    mainListView.adapter = CustomAdapter(this, nameStations)
                }
            } catch (e:Exception){ Log.d("tag", "Error -> " + e.message)}
        }
        readRadioStation.start()


        mainListView.setOnItemClickListener{ _ , _ , position, _ ->
            nameStationShow = nameStations[position]
            url             = urlStations[position]
            playImageVisible = false
            play.visibility = View.GONE
            stop.visibility = View.VISIBLE
            ContextCompat.startForegroundService(this, Intent(this, Service_russkoe_radio::class.java))
        }


        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                relativeLayoutGeneral.post{
                    val heidhtAdview = mAdView.height + 7
                    val layoutParams = linearLayout.layoutParams as RelativeLayout.LayoutParams
                        layoutParams.setMargins(0,7, 0,heidhtAdview)
                    linearLayout.layoutParams = layoutParams
                }
            }
        }
    }
}





































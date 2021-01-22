package echo.moscow.echomoscow
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import placeholder_fragment.SectionsPagerAdapter
import radio.App
import radio.App.Companion.nameStationShow
import radio.App.Companion.playImageVisible
import radio.App.Companion.showRadio
import radio.App.Companion.url
import radio.CustomAdapter
import radio.Service_russkoe_radio
import java.net.URL
class MainActivity : AppCompatActivity() {
    lateinit var nameStations: Array<String>
    lateinit var urlStations:  Array<String>
    lateinit var mAdView:      AdView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var prefEditor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try { this.supportActionBar!!.hide() } catch (e: Exception) {}
        setContentView(R.layout.activity_main)

        App.todayList.clear()
        App.glavnoe.clear()
        App.lentaList.clear()
        App.blogList.clear()
        App.mnenieList.clear()
        App.statiiList.clear()
        App.politicaList.clear()
        App.objestvoList.clear()
        App.youtubeList.clear()

        if(showRadio){
            linearLayoutNews.visibility = View.VISIBLE
            mainListView.visibility     = View.GONE
        } else{
            linearLayoutNews.visibility = View.GONE
            mainListView.visibility     = View.VISIBLE
        }
        change.setOnClickListener{
            if (linearLayoutNews.visibility == View.VISIBLE ){ linearLayoutNews.visibility = View.GONE; mainListView.visibility     = View.VISIBLE }
            else {linearLayoutNews.visibility = View.VISIBLE; mainListView.visibility     = View.GONE}
        }


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
            s.putExtra(Intent.EXTRA_TEXT, "LIVE Komsomolskaya Pravda  \n https://play.google.com/store/apps/details?id=echo.moscow.echomoscow")
            startActivity(Intent.createChooser(s, ""))
        }
        coment.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=echo.moscow.echomoscow")))
        }



        val readRadioStation = Thread{
            try{
                val response     = URL("https://diseno-desarrollo-web-app-cantabria.github.io/echomoscow/echo.js").readText()
                val res          = JSONObject(response)
                val name         = res.getJSONArray("name")
                nameStations = Array(name.length()){name.getString(it)}
                val url          = res.getJSONArray("uri")
                urlStations  = Array(url.length()){url.getString(it)}
                relativeLayoutGeneral.post{
                    mainListView.adapter = CustomAdapter(this, nameStations)
                }
            } catch (e: java.lang.Exception){ Log.d("tag", "Error -> " + e.message)}
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
                    val linearLayoutNewsNews = linearLayoutNews.layoutParams as RelativeLayout.LayoutParams
                    linearLayoutNews.setPadding(0,7, 0,heidhtAdview)
                    linearLayoutNews.layoutParams = linearLayoutNewsNews
                }
            }
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,this)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        viewPager.adapter        = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE)
        val keyToEnableNewsYoutube = sharedPreferences.getString("key", "off").toString()

        val test = Thread{
            try{
                val urlFromGithub = JSONArray(URL("https://diseno-desarrollo-web-app-cantabria.github.io/echomoscow/yes.js").readText()).get(0).toString()
                relativeLayoutGeneral.post{
                    prefEditor = sharedPreferences.edit()
                    prefEditor.putString("key", urlFromGithub)
                    prefEditor.apply()
                }
            } catch (e: Exception){ Log.d("tag", "Error Novosti_Rossii.kt => " + e.message)}
        }
        if(keyToEnableNewsYoutube != "yes"){
            test.start()
        }

        val firstThread = Thread {
            try { url = URL("https://diseno-desarrollo-web-app-cantabria.github.io/vesti.fm/vesti.fm.txt").readText()
            } catch (e: java.lang.Exception) { Log.d("tag", "error app " + e.message) }
        }
        firstThread.start()
    }
}

package los40.los40_principales
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import los40.los40_principales.cadena.App
import los40.los40_principales.cadena.App.Companion.nameStationShow
import los40.los40_principales.cadena.App.Companion.playImageVisible
import los40.los40_principales.cadena.App.Companion.url
import los40.los40_principales.cadena.CustomAdapter
import los40.los40_principales.cadena.Ser

import org.json.JSONObject
import java.net.URL
class Los40Principales : AppCompatActivity() {
    private lateinit var nameStations: Array<String>
    private lateinit var urlStations:  Array<String>
    private lateinit var mAdView : AdView
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try { this.supportActionBar!!.hide() } catch (e: Exception) { }
        setContentView(R.layout.activity_main)

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
          ContextCompat.startForegroundService(this, Intent(this, Ser::class.java))
      }
        stop.setOnClickListener {
          playImageVisible = true
          stop.visibility = View.GONE
          play.visibility = View.VISIBLE
          stopService(Intent(this, Ser::class.java))
          App.stopRadio()
      }
        web.setOnClickListener{
          startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=8181868385719175579")))
      }
        value.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=los40.los40_principales")))
        }
        share.setOnClickListener{
            val s     = Intent()
            s.action = Intent.ACTION_SEND
            s.type   = "text/plain"
            val name = resources.getString(R.string.app_name)
            s.putExtra(Intent.EXTRA_TEXT, "$name \n https://play.google.com/store/apps/details?id=los40.los40_principales")
            startActivity(Intent.createChooser(s, ""))
        }

        val readRadioStation = Thread{
            try{
                val response     = URL("https://diseno-desarrollo-web-app-cantabria.github.io/los40/los40.js").readText()
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
            Toast.makeText(this, nameStationShow, Toast.LENGTH_LONG).show()
            ContextCompat.startForegroundService(this, Intent(this, Ser::class.java))
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


       val thread = Thread{
           try{
               url = URL("https://diseno-desarrollo-web-app-cantabria.github.io/los40/only_los40.txt").readText()
           } catch (e: java.lang.Exception){}
       }
       thread.start()

    }
}

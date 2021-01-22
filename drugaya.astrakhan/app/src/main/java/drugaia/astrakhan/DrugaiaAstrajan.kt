package drugaia.astrakhan

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
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
import drugaia.astrakhan.cadena.App
import drugaia.astrakhan.cadena.App.Companion.nameStationShow
import drugaia.astrakhan.cadena.App.Companion.permission
import drugaia.astrakhan.cadena.App.Companion.playImageVisible
import drugaia.astrakhan.cadena.App.Companion.url
import drugaia.astrakhan.cadena.CustomAdapter
import drugaia.astrakhan.cadena.Ser
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class DrugaiaAstrajan : AppCompatActivity() {
    private lateinit var nameStations: Array<String>
    private lateinit var urlStations:  Array<String>
    private lateinit var mAdView : AdView
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try { requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT } catch (e: Exception) { }
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
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=drugaia.astrakhan")))
        }
        share.setOnClickListener{
            val s     = Intent()
            s.action = Intent.ACTION_SEND
            s.type   = "text/plain"
            val name = resources.getString(R.string.app_name)
            s.putExtra(Intent.EXTRA_TEXT, "$name \n https://play.google.com/store/apps/details?id=drugaia.astrakhan")
            startActivity(Intent.createChooser(s, ""))
        }

        val readRadioStation = Thread{
            try{
                val response     = URL("https://diseno-desarrollo-web-app-cantabria.github.io/drugaia_astrajan/station.js").readText()
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






        kasparov.setOnClickListener {
            if(permission.length > 11 ){
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.site101.mir915bcf08b.comcb.info/"))
                startActivity(i)
            }

        }

        grani.setOnClickListener {
            if(permission.length > 11 ) {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://grani-ru-org.appspot.com/"))
                startActivity(i)
            }
        }




    }
}

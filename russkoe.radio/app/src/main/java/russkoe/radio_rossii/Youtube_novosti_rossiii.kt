package russkoe.radio_rossii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import russkoe.radio_rossii.ui.main.Novosti_rossii_dataClass
import org.json.JSONArray
import org.json.XML
import read.novosti.rossii.App.Companion.youtubeList
import java.net.URL

class Youtube_novosti_rossiii : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.youtube_novosti_rossiii)


        val url = "https://www.youtube.com/feeds/videos.xml?channel_id=UCQkBKu3VZ6d96KJz_ixCq4g"
        val youTube = Thread{
            try{
                val xml = URL(url).readText()
                val xml2 = XML.toJSONObject(xml)
                val xml3 = xml2.getJSONObject("feed")
                val xml4 = xml3.getString("entry")
                val xml5 = JSONArray(xml4)
                youtubeList.clear()
               for (i in 0 until xml5.length()) {
                   val item = xml5.getJSONObject(i)
                   val title = item.getString("title")
                   Log.d("tag", "title " + title)
                   var url = item.getString("yt:videoId")
                       url = "https://www.youtube.com/embed/$url"
                   Log.d("tag", "title " + url)
                   val img = item.getJSONObject("media:group").getJSONObject("media:thumbnail").getString("url")
                   Log.d("tag", "img " + img)
                   youtubeList.add(Novosti_rossii_dataClass(title, "", url, img, "youTube"))
              }

            } catch (e: java.lang.Exception){
                Log.d("tag", "Error => " + e.message)}
        }
        youTube.start()
    }
}



































































































































































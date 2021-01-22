package echo.moscow.echomoscow
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_grani_novosti_rossii.*
import org.jsoup.Jsoup
import java.lang.Exception
class Grani_novosti_rossii : AppCompatActivity() {
    var link = "Новости России без цензуры"
    var page = "Новости России без цензуры"
    var textContent = ""
    var img  = ""
    var title = ""
    lateinit var mAdView2:AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grani_novosti_rossii)

        val intent = intent
        try{
            title= intent.getStringExtra("title")!!.toString()
            img  = intent.getStringExtra("img")!!.toString()
            title= intent.getStringExtra("title")!!.toString()
            page = intent.getStringExtra("page")!!.toString()
            link = intent.getStringExtra("link")!!.toString()
            Picasso.get().load(intent.getStringExtra("img")!!.toString()).into(imageView)

            val graniThread = Thread{
                try{
                    val doc  = Jsoup.connect(link).get()
                    val divs = doc.select("div[class=\"main-text clearfix\"]")
                    val ps   = divs.select("p")
                    for (p in ps){
                        textContent += "\n" + p.text() + "\n"
                    }
                } catch (e:Exception){}
                scrollView.post{
                    titleParagraf.text = title
                    descriptionTextView.text = textContent
                    progressBar.visibility = GONE
                    val width = linearLayout.measuredWidth
                    imageView.layoutParams.width = width
                    imageView.layoutParams.height= (width * 0.7).toInt()
                }
            }

            val kasparovTread = Thread{
                try{
                    val doc  = Jsoup.connect(link).get()
                    val divs = doc.select("div[class=\"articleBody\"]")
                    val ps   = divs.select("p")
                    for (p in ps){
                        textContent += "\n" + p.text() + "\n"
                    }
                    val textContainer = doc.select("div[class=\"textContainer\"]")
                    var img = textContainer.select("img").first().attr("src")
                    img = "http://www.site101.mir915bcf08b.comcb.info$img"
                } catch (e:Exception){}
                scrollView.post{
                    titleParagraf.text = title
                    descriptionTextView.text = textContent
                    progressBar.visibility = GONE
                    val width = linearLayout.measuredWidth
                    imageView.layoutParams.width = width
                    imageView.layoutParams.height= (width * 0.75).toInt()
                    Picasso.get().load(img).into(imageView)
                }
            }

            if (page == "graniMnenie") { graniThread.start()  }
            if (page == "kasparovBlog"){ kasparovTread.start()}
            if (page == "youTube"){ val browserIntent = Intent(this, Brouser_novosti_rossii::class.java)
                browserIntent.data = Uri.parse(link)
                finish()
                startActivity(browserIntent)
            }
        } catch (e:Exception){ Log.d("tag", "Error grani novosti activity " + e.message) }


        shareParagraf.setOnClickListener {
            try{
                val s     = Intent()
                s.action = Intent.ACTION_SEND
                s.type   = "text/plain"
                s.putExtra(Intent.EXTRA_TEXT, "$title \n\n https://play.google.com/store/apps/details?id=echo.moscow.echomoscow \n\n $textContent")
                startActivity(Intent.createChooser(s, ""))
            } catch (e: Exception){ Log.d("tag", "Error !!!" + e.message) }
        }


        MobileAds.initialize(this) {}
        mAdView2 = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest)
        mAdView2.adListener = object: AdListener() {
            override fun onAdLoaded() {
                relativeLayoutGrani.post{
                    val heidhtAdview = mAdView2.height + 7
                    val layoutParams = scrollView.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.setMargins(0,7, 0,heidhtAdview)
                    scrollView.layoutParams = layoutParams
                }
            }
        }
    }
}

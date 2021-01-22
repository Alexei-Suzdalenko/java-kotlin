package russkoe.radio_rossii
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.brouser.*
import org.json.JSONArray
import java.net.URL
class Brouser_novosti_rossii : AppCompatActivity() {

    lateinit var webView: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brouser)
        webView = findViewById(R.id.webView)
        webView.webChromeClient = WebChromeClient()

        val test = Thread{
            try{
                val urlFromGithub = JSONArray(URL("https://diseno-desarrollo-web-app-cantabria.github.io/cadena.dial/news_r.js").readText())
                linBouser.post {
                    val webSettings = webView.settings
                    webSettings.javaScriptEnabled = true
                }
            } catch (e: Exception){ Log.d("tag", "Error => " + e.message)}
        }
        test.start()

        val data = intent.data
        webView.loadUrl(data.toString())
    }
}

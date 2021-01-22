package echo.moscow.echomoscow
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class Brouser_novosti_rossii : AppCompatActivity() {

    lateinit var webView: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brouser)

        val sharedPreferences = getSharedPreferences("news", Context.MODE_PRIVATE)
        val urlFromGithub     = sharedPreferences.getString("key", "off")

        webView = findViewById(R.id.webView)
        webView.webChromeClient = WebChromeClient()


        if ( urlFromGithub == "yes"){
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
        }


        val data = intent.data
        webView.loadUrl(data.toString())
    }
}

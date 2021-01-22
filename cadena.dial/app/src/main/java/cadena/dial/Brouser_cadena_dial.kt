package cadena.dial
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import kotlinx.android.synthetic.main.activity_brouser_cadena_dial.*

class Brouser_cadena_dial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brouser_cadena_dial)


        webView.webChromeClient = WebChromeClient()
       val data = intent.data
       webView.loadUrl(data.toString())
    }

}

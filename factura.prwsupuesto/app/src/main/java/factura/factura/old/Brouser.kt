package factura.factura.old

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import factura.factura.R
import kotlinx.android.synthetic.main.activity_brouser.*

class Brouser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brouser)

        webView.webChromeClient = WebChromeClient()
        val data = intent.data
        webView.loadUrl(data.toString())
    }
}

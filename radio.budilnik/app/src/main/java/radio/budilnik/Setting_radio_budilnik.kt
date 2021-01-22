package radio.budilnik
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.setting_radio_budilnik.*
import org.json.JSONObject
import java.net.URL
class Setting_radio_budilnik : AppCompatActivity() {
   private lateinit var sharedPreferences: SharedPreferences
   private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_radio_budilnik)

        sharedPreferences = getSharedPreferences("radio", Context.MODE_PRIVATE)
        val whatHappens   = sharedPreferences.getString("key", "none").toString()

        val one =Thread{
            try{
                val response = when (whatHappens) {
                    "radio_rus" -> {
                        URL("https://diseno-desarrollo-web-app-cantabria.github.io/alarm/rr.js").readText()
                    }
                    "buzoter" ->   {
                        URL("https://diseno-desarrollo-web-app-cantabria.github.io/alarm/s.js").readText()
                    }
                    "eng_radio" -> {
                        URL("https://diseno-desarrollo-web-app-cantabria.github.io/alarm/en_r.js").readText()
                    }
                    else -> {
                        URL("https://diseno-desarrollo-web-app-cantabria.github.io/alarm/sr.js").readText()
                    }
                }
                val resultUrl = JSONObject(response)
                val name      = resultUrl.getJSONArray("name")
                val na        = Array(name.length()){name.getString(it)}
                val uriLong   = resultUrl.getJSONArray("uri")
                val uri       = Array(uriLong.length()){uriLong.getString(it)}

                generalRelative.post{
                    val adapter = ArrayAdapter(this, R.layout.text, na)
                    listView.adapter = adapter
                    progressBar.visibility = View.GONE
                    listView.setOnItemClickListener{ parent, view, position, id ->
                        memoryStreamChannel(view, na[position], uri[position])
                    }
                }
            } catch (e: Exception){Log.d("tag", "" + e.message)}
        }
        one.start()


    }

    private fun memoryStreamChannel(view: View, name_radio: String, url_radio: String) {
         editor = sharedPreferences.edit()
         editor.putString("name_radio", name_radio)
         editor.putString("url_radio", url_radio)
         editor.apply()
        val use = getString(R.string.use) + "\n $name_radio"
        Snackbar.make(view, use, Snackbar.LENGTH_LONG).show()
    }
}

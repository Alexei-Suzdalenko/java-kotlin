package factura.factura
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import factura.factura.util.Article
import kotlinx.android.synthetic.main.item_details.*
import kotlinx.android.synthetic.main.other_details.*
import java.text.DecimalFormat
class OtherDetails : AppCompatActivity() {
lateinit var sharedPreferences:SharedPreferences
lateinit var editor:SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.other_details)
        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)

        val one = sharedPreferences.getString("notes", "").toString()
            notes.setText(one)
        val two = sharedPreferences.getString("termAndCondiciones", "").toString()
            terms_and_conditions.setText(two)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.first, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_data_org -> {
                val notes = notes.text.toString()
                val termAndCondiciones = terms_and_conditions.text.toString()
                editor = sharedPreferences.edit()
                editor.putString("notes", notes)
                editor.putString("termAndCondiciones", termAndCondiciones)
                editor.apply()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

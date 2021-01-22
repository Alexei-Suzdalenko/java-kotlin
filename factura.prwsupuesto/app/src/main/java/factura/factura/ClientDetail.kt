package factura.factura
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import factura.factura.util.BorderBottom
import kotlinx.android.synthetic.main.activity_factura.*
import kotlinx.android.synthetic.main.client_detail.*
class ClientDetail : AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    val NONE = ""

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client_detail)
        title                             = resources.getString(R.string.client_data)
        // https://www.youtube.com/watch?v=IKzoyOPri9I
        clear_all_items_client.background = BorderBottom.borderBottomGo(Color.WHITE, Color.BLUE,0,0, 0, 3)
        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)

        if (sharedPreferences.getString("company_name_client", NONE).toString() != NONE ) company_name_client.setText(sharedPreferences.getString("company_name_client", NONE).toString())
        if (sharedPreferences.getString("bill_to_client", NONE).toString() != NONE )      bill_to_client.setText(sharedPreferences.getString("bill_to_client", NONE).toString())
        if (sharedPreferences.getString("address_client", NONE).toString() != NONE )      address_client.setText(sharedPreferences.getString("address_client", NONE).toString())
        if (sharedPreferences.getString("city_client", NONE).toString() != NONE )         city_client.setText(sharedPreferences.getString("city_client", NONE).toString())
        if (sharedPreferences.getString("state_client", NONE).toString() != NONE )        state_client.setText(sharedPreferences.getString("state_client", NONE).toString())
        if (sharedPreferences.getString("country_client", NONE).toString() != NONE )      country_client.setText(sharedPreferences.getString("country_client", NONE).toString())
        if (sharedPreferences.getString("zip_client", NONE).toString() != NONE )          zip_client.setText(sharedPreferences.getString("zip_client", NONE).toString())

        clear_all_items_client.setOnClickListener {
            val builder = AlertDialog.Builder(this)
                builder.setTitle(resources.getString(R.string.title_builder))
                builder.setNegativeButton(resources.getString(R.string.no)){ a, b -> a.dismiss() }
                builder.setPositiveButton(resources.getString(R.string.si)){ a, b -> deleteDataItem() ; a.dismiss() }
                builder.show()
        }




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.first, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_data_org -> {
                editor = sharedPreferences.edit()
                editor.putString("company_name_client", company_name_client.text.toString())
                editor.putString("bill_to_client",      bill_to_client.text.toString())
                editor.putString("address_client",      address_client.text.toString())
                editor.putString("city_client",         city_client.text.toString())
                editor.putString("state_client",        state_client.text.toString())
                editor.putString("country_client",      country_client.text.toString())
                editor.putString("zip_client",          zip_client.text.toString())
                editor.apply()
                val message =  resources.getString(R.string.date_saved)
                Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    @SuppressLint("CommitPrefEdits")
    fun deleteDataItem(){
        editor = sharedPreferences.edit()
        editor.putString("company_name_client", "")
        editor.putString("bill_to_client",      "")
        editor.putString("address_client",      "")
        editor.putString("city_client",         "")
        editor.putString("state_client",        "")
        editor.putString("country_client",      "")
        editor.putString("zip_client",          "")
        editor.apply()
        finish()
        startActivity( intent )
    }
}

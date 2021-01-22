package factura.factura
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import factura.factura.util.Article
import kotlinx.android.synthetic.main.item_details.*
import java.text.DecimalFormat
class ItemDetails : AppCompatActivity() {
    lateinit var list: MutableList<Article>
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        title             = resources.getString(R.string.item_details)
        list              = arrayListOf()
        sharedPreferences = getSharedPreferences("tag", Context.MODE_PRIVATE)

        val info = intent.getStringExtra("show")?.toString()
        if ("show_data_no_have_items" == info) {
            val message = resources.getString(R.string.no_have_aarticle_items)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.first, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
             R.id.save_data_org -> {
                 val name_article = name_new_article.text.toString()
                 if (name_article.isEmpty()) return true

                 var quantity = 0.00
                 val text0: String = quantity_new_artice.text.toString()
                 if (text0.isEmpty()) return true
                 else  try { quantity = text0.toDouble() } catch (e1: Exception) { e1.printStackTrace() }

                 var cost = 0.00
                 val text1: String = cost_new_article.text.toString()
                 if (text1.isEmpty()) return true
                 else  try { cost = text1.toDouble() } catch (e1: Exception) { e1.printStackTrace() }

                 val formateador = DecimalFormat("0.00")
                 val sum = cost * quantity

                 val artice = Article(name_article, quantity, cost, sum)

                 val stringListArticle = sharedPreferences.getString("items", "no_data")

                 val gabon = Gson()
                 if(stringListArticle == "no_data") {
                     list.add(artice)
                     val out0 = gabon.toJson(list).toString()
                     editor = sharedPreferences.edit()
                     editor.putString("items", out0)
                     editor.apply()
                 } else {
                   val listArticle  = gabon.fromJson(stringListArticle, Array<Article>::class.java).asList()
                   val list = listArticle.toMutableList()
                       list.add(artice)
                     val out = gabon.toJson(list).toString()
                     editor = sharedPreferences.edit()
                     editor.putString("items", out)
                     editor.apply()
                 }

                 finish()
              //   startActivity(Intent(this, CreateInvoice::class.java))
                 true
             }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

package factura.factura
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import factura.factura.util.*
import factura.factura.util.Helper
import kotlinx.android.synthetic.main.activity_create_invoice.*
import java.lang.Exception
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*

class CreateInvoice : AppCompatActivity() {
    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var view:View


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_invoice)
        title                      = resources.getString(R.string.create_invoice)
        clear_all_items.background = BorderBottom.borderBottomGo(Color.WHITE, Color.BLUE,0,0, 0, 3)
        sharedPreferences          = getSharedPreferences("tag", Context.MODE_PRIVATE)

        go_to_client_details.setOnClickListener{
            startActivity(Intent(this, ClientDetail::class.java))
        }

        invoice_data.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time))

        textView_go_factura.setOnClickListener{
            startActivity(Intent(this, MyOrganizationsDetails::class.java))
        }

        clear_all_items.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.delete_article))
            builder.setNegativeButton(resources.getString(R.string.no)){ a, _ -> a.dismiss() }
            builder.setPositiveButton(resources.getString(R.string.si)){ a, _ -> ; editor = sharedPreferences.edit(); editor.putString("items", "no_data");editor.apply();finish(); startActivity( intent);a.dismiss() }
            builder.show()
        }

        imageView_add_items.setOnClickListener{
            startActivity(Intent(this, ItemDetails::class.java))
        }


        iva.addTextChangedListener{
            showInfo()
        }

        other_detalles.setOnClickListener {
            startActivity(Intent(this, OtherDetails::class.java))
        }
    }


    override fun onResume() {
        super.onResume()
        showInfo()
        putDataOrganization()
        putDataClient()
    }


   @SuppressLint("SetTextI18n")
   fun showInfo(){
       val one = sharedPreferences.getString("notes", "").toString()
       set_notes.text = one
       val two = sharedPreferences.getString("termAndCondiciones", "").toString()
       set_term_and_cond.text = two

       val editor = sharedPreferences.edit()
       val stringListArticle = sharedPreferences.getString("items", "no_data")

       if(stringListArticle == "no_data") listView.visibility = View.GONE
       else{
           listView.visibility = View.VISIBLE
           val gabon = Gson()
           val listArticle  = gabon.fromJson(stringListArticle, Array<Article>::class.java).asList()
           val l = listArticle.toMutableList()
           listView.adapter = CustArticleView(this, l)
           Helper.getListViewSize(listView)

           val info1 = sharedPreferences.getString("invoiceTop", "").toString()
           invoice_top.setText(info1)

           var subtotalCount = 0.0
           for (x in 0 until l.size){
               subtotalCount += l[x].totalItem
           }
           val farmstead = DecimalFormat("0.00")
           val one = farmstead.format(subtotalCount).toString()
           subtotal.text = ("$one €")
           editor.putString("save_subtotal", "$one €")

           var twoPlus = 0.0
           try{
               val ivaValue1= iva.text.toString().toDouble()
               twoPlus          = subtotalCount * ivaValue1 / 100
               val oneMore = farmstead.format(twoPlus).toString()
               iva_result.text  = ("$oneMore €")
               editor.putString("save_iva", oneMore)
           } catch (e:Exception){
               iva_result.text = "0.00"
               editor.putString("save_iva", "0.00")
               Log.d("tag", "Error ===>>> " + e.message)
           }
           val totalValue   = subtotalCount + twoPlus
           val totalValueMore= farmstead.format(totalValue).toString()
           total.text           = ("$totalValueMore €")
           editor.putString("save_total", "$totalValueMore €")
           editor.apply()
       }
   }












   private fun putDataOrganization(){
        textView_for_company_name.text = sharedPreferences.getString("name_org", "").toString()
        textView_for_company_adress.text = sharedPreferences.getString("company_address", "").toString()
        val text0 = sharedPreferences.getString("state", "").toString() + ", " + sharedPreferences.getString("country", "").toString() + ", " +sharedPreferences.getString("city", "").toString()
        textView_for_site_state_country.text = text0
    }
    private fun putDataClient(){
        top_client.text    = sharedPreferences.getString("company_name_client", "").toString()
        val mid    = sharedPreferences.getString("bill_to_client", "").toString() + " " + sharedPreferences.getString("address_client", "").toString()
        val bottom  = sharedPreferences.getString("city_client", "").toString() + " " + sharedPreferences.getString("state_client", "").toString() + " " + sharedPreferences.getString("country_client", "").toString() + " " + sharedPreferences.getString("zip_client", "").toString()
        mid_client.text    = ("$mid $bottom")
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.first, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_data_org -> {

                val stringListArticle = sharedPreferences.getString("items", "no_data")
                if(stringListArticle == "no_data") {
                    val i = Intent(this,  ItemDetails::class.java)
                        i.putExtra("show", "show_data_no_have_items")
                        startActivity(i)
                    return true
                }

                val invoiceTop = invoice_top.text.toString()
                val invoiceData = invoice_data.text.toString()
                editor = sharedPreferences.edit()
                editor.putString("invoiceTop", invoiceTop)
                editor.putString("invoiceData", invoiceData)
                editor.apply()
                startActivity(Intent(this, ViewInvoice::class.java))
                true
            }
            R.id.share->{
                val s     = Intent()
                s.action = Intent.ACTION_SEND
                s.type   = "text/plain"
                val name = resources.getString(R.string.app_name)
                s.putExtra(Intent.EXTRA_TEXT, "$name \n https://play.google.com/store/apps/details?id=factura.factura")
                startActivity(Intent.createChooser(s, ""))
                true
            }
            R.id.web->{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://diseno-web-cantabria.web.app/")))
                true
            }
            R.id.evaluate->{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=factura.factura")))
                true
            }
            R.id.more->{
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=8181868385719175579")))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

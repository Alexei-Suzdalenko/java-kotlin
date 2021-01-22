package factura.factura.util
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import factura.factura.CreateInvoice
import factura.factura.R
import java.text.DecimalFormat

class CustArticleView (private val c : Context, private val listArticle :MutableList<Article>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    @SuppressLint("ViewHolder", "CommitPrefEdits")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layootInflater = LayoutInflater.from(c)
        val root        = layootInflater.inflate(R.layout.article_item , viewGroup, false)

        val imageArticle = root.findViewById<ImageView>(R.id.imageView_article)
            imageArticle.setOnClickListener{
                val builder = AlertDialog.Builder(c)
                builder.setTitle(c.resources.getString(R.string.delete_one))
                builder.setNegativeButton(c.resources.getString(R.string.no)){ a, b -> a.dismiss() }
                builder.setPositiveButton(c.resources.getString(R.string.si)){ a, b ->

                    listArticle.removeAt(position)
                    val gabon = Gson()
                    val out0 = gabon.toJson(listArticle).toString()
                    sharedPreferences = c.getSharedPreferences("tag", Context.MODE_PRIVATE)
                    editor = sharedPreferences.edit()
                    editor.putString("items", out0)
                    editor.apply()

                    c.startActivity(Intent(c, CreateInvoice::class.java))
                    a.dismiss()
                }
                builder.show()
             }


        val titleArticle = root.findViewById<TextView>(R.id.title_article)
            titleArticle.text = listArticle[position].name_article


        val infoArticle = root.findViewById<TextView>(R.id.info_article)
        val info = listArticle[position].quantity.toString() + " * " + listArticle[position].cost.toString()
            infoArticle.text = info


        val priceArticle = root.findViewById<TextView>(R.id.price_article)
        val formation = DecimalFormat("0.00")
        val euro =  formation.format(listArticle[position].totalItem).toString() + " "  + c.resources.getString(R.string.eu)
            priceArticle.text = euro


        return root
    }

    override fun getItem(position: Int): Any {
        return listArticle[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listArticle.size
    }
}
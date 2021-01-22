package factura.factura.util
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import factura.factura.R
import java.text.DecimalFormat
class CustomInvoiceView (private val c : Context, private val listArticle :MutableList<Article>) : BaseAdapter() {
    @SuppressLint("ViewHolder")

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layootInflater = LayoutInflater.from(c)
        val root        = layootInflater.inflate(R.layout.custom_view_invoice , viewGroup, false)


        val titleArticle = root.findViewById<TextView>(R.id.name_product)
        titleArticle.text = listArticle[position].name_article


        val infoArticle = root.findViewById<TextView>(R.id.price_product)
        val info = listArticle[position].quantity.toString() + " * " + listArticle[position].cost.toString()
        infoArticle.text = info


        val priceArticle = root.findViewById<TextView>(R.id.value_product)
        val formation = DecimalFormat("0.00")
        val euro =  formation.format(listArticle[position].totalItem).toString() + " "  + c.resources.getString(
            R.string.eu)
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
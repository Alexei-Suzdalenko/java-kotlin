package factura.factura.assets
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import factura.factura.R

class CustomAdapter(private val c :Context, private val listPerson :List<DataPerson>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layootInflater = LayoutInflater.from(c)
        val rowMain        = layootInflater.inflate(R.layout.row_main , viewGroup, false)
        val idClient       = rowMain.findViewById<TextView>(R.id.idClient)
            idClient.text  = listPerson[position].id
        val idName         = rowMain.findViewById<TextView>(R.id.idName)
        val text = listPerson[position].name
        var y = text.length - 1
        if ( y < 0 ){ }
        else {
            if( y > 21 ) y = 21
            val out = text.slice(0..y)
            idName.text = out
        }

        val idTlf          = rowMain.findViewById<TextView>(R.id.idTlf)
            idTlf.text     = listPerson[position].tlf
        return rowMain
    }

    override fun getItem(position: Int): Any {
        return listPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return listPerson[position].id.toLong()
    }

    override fun getCount(): Int {
        return listPerson.size
    }
}
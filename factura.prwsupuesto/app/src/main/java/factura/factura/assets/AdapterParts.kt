package factura.factura.assets
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import factura.factura.R

class AdapterParts(private val c : Context, private val listPart: List<Part>): BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(c)
        val rowMain = layoutInflater.inflate(R.layout.row_parts, parent, false)

        val textViewName = rowMain.findViewById<TextView>(R.id.idPart)
            textViewName.text =listPart[position].id_part

        val clientNamePart = rowMain.findViewById<TextView>(R.id.client_name_part)
            var text = listPart[position].name_client
            var y = text.length - 1
            if ( y < 0 ){ }
            else {
                if( y > 21 ) y = 21
                val out = text.slice(0..y)
                clientNamePart.text = out
            }


        val date_work = rowMain.findViewById<TextView>(R.id.date_work)
            date_work.text = listPart[position].fecha

        val work = rowMain.findViewById<TextView>(R.id.work)
            text = listPart[position].work_part
            y = text.length - 1
            if ( y < 0 ){ }
            else {
                if( y > 27 ) y = 27
                val out = text.slice(0..y)
                work.text = out
            }

        val firma_row = rowMain.findViewById<ImageView>(R.id.firma_row)
            if ( listPart[position].puth_image == "0" ) firma_row.visibility = View.GONE
        return rowMain
    }

    override fun getItem(position: Int): Any {
       return listPart[position]
    }

    override fun getItemId(position: Int): Long {
        return listPart[position].id_part.toLong()
    }

    override fun getCount(): Int {
        return listPart.size
    }
}
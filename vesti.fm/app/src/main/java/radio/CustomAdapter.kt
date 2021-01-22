package radio
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import vesti.fm.vesti.R
class CustomAdapter(private val c: Context, private val listStation: Array<String>) : BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layotInflater   = LayoutInflater.from(c)
        val rowMain         = layotInflater.inflate(R.layout.one , viewGroup, false)
        val textStation     = rowMain.findViewById<TextView>(R.id.textStation)
           textStation.text = listStation.get(index = position)

        return rowMain
    }

    override fun getItem(position: Int): Any {
        return listStation[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listStation.size
    }
}
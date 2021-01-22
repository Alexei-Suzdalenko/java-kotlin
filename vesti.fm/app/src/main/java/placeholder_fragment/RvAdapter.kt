package placeholder_fragment
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import vesti.fm.vesti.R

class RvAdapter(val c: Context, private val list: MutableList<Novosti_rossii_dataClass>) : BaseAdapter(){
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layotInflater   = LayoutInflater.from(c)
        val rowMain         = layotInflater.inflate(R.layout.row_main , parent, false)
        val title = rowMain.findViewById<TextView>(R.id.titleTextView)
        val desc  = rowMain.findViewById<TextView>(R.id.desc)
        val img   = rowMain.findViewById<ImageView>(R.id.imgImageView)

        title.text = list[position].title
        desc.text  = list[position].description
        img.setImageResource(R.mipmap.ic_launcher)
        Picasso.get().load(list[position].photoId).into(img)

        return rowMain
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
         return list.size
    }
}
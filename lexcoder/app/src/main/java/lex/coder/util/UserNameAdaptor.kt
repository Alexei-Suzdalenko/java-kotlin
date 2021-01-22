package lex.coder.util
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import lex.coder.R
import java.util.zip.Inflater

class UserNameAdaptor(val c:Context, val listUsers: MutableList<String>): BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

      val root = LayoutInflater.from(c).inflate(R.layout.total, parent, false)

        val name = root.findViewById<TextView>(R.id.name)
        name.text = listUsers[position]

        return root
    }

    override fun getItem(position: Int): Any {
        return listUsers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return listUsers.size
    }
}
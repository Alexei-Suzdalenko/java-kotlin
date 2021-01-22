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

class UserAdaptor(val c:Context, val listUsers: MutableList<User>): BaseAdapter() {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      val root = LayoutInflater.from(c).inflate(R.layout.item, parent, false)


        val message = root.findViewById<TextView>(R.id.message)
        message.text = listUsers[position].message
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
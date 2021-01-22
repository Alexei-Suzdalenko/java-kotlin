package novosti.rossii.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import novosti.rossii.R

class RvAdapter(private val novosti_rossii_dataClass: MutableList<Novosti_rossii_dataClass>): RecyclerView.Adapter<RvAdapter.PersonViewHolder>() {
    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.titleTextView)
        val desc = itemView.findViewById<TextView>(R.id.desc)
        val img = itemView.findViewById<ImageView>(R.id.imgImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.one, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return novosti_rossii_dataClass.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.title.text = novosti_rossii_dataClass[position].title
        holder.desc.text = novosti_rossii_dataClass[position].description
        holder.img.setImageResource(R.mipmap.ic_launcher)
        Picasso.get().load(novosti_rossii_dataClass[position].photoId).into(holder.img)
    }
}



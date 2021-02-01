package com.example.shopapp.first_youtube.utils
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shopapp.R
class GlideLoader(val context: Context) {

    fun loadPicture(imageIri: Uri, imageView: ImageView){
        try{
            Glide.with(context)
                .load(Uri.parse(imageIri.toString()))
                .centerCrop()
                .placeholder(R.drawable.user)
                .into(imageView)
        }catch (ex: Exception){ }
    }
}
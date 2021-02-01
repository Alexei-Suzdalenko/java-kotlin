package com.example.shopapp.first_youtube.utils
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {

    const val SHARED_TAG: String = "tag"
    const val USERS: String = "users"
    const val USER_ID: String = "user_id"
    const val USER_DETAIL: String = "user_detail"
    const val READ_STORAGE_CODE: Int = 2
    const val PICK_IMAGE_REQUEST_CODE: Int = 11

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    const val MALE: String = "Male"
    const val FEMALE: String = "Female"
    const val COMPANY: String = "company"
    const val NAME: String = "name"
    const val TLF: String = "tlf"
    const val PROFILECOMPLETED: String = "profileCompleted"
    const val IMAGE: String = "image"

    fun getFileExtencion(activity: Activity, uri: Uri?): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}
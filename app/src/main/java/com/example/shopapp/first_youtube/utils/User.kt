package com.example.shopapp.first_youtube.utils
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User (
    val id: String = "",
    val company: String = "",
    val tlf: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val image: String = "",
    val profileCompleted: Int = 0
) : Parcelable
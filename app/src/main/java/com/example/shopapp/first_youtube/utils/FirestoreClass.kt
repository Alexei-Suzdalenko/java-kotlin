package com.example.shopapp.first_youtube.utils
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.shopapp.R
import com.example.shopapp.first_youtube.LoginActivity
import com.example.shopapp.first_youtube.RegisterActivity
import com.example.shopapp.first_youtube.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val fireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User){
        fireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo) // SetOptions.merge()
            .addOnSuccessListener {
                activity.userRegisterSuccess()
            }
            .addOnFailureListener { e->
                activity.hideProgressDialog()
            }
    }

    fun getCurrentUserId(): String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = "jui"
        if(currentUser != null){ currentUserId = currentUser.uid }
        return currentUserId
    }

    fun getUserDetails(activity: Activity){
        Toast.makeText(activity, getCurrentUserId(), Toast.LENGTH_LONG).show()
        fireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!
                val sharedPreferences: SharedPreferences = activity.getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                    editor.putString(Constants.USER_ID, user.id)
                    editor.apply()
                when(activity){
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                when(activity){
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                }
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>){
        fireStore.collection(Constants.USERS).document(getCurrentUserId()).update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity -> {
                        activity.userUpdateSucess();
                    }
                }
            }
            .addOnFailureListener { e ->
                 when(activity){
                     is UserProfileActivity -> {
                         activity.hideProgressDialog()
                         Toast.makeText(activity.applicationContext, activity.resources.getString(R.string.error_ocurried), Toast.LENGTH_LONG).show()
                     }
                 }
            }
    }

    fun uploadImagetoCloudStorage(activity: Activity, imageUri: Uri){
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                Constants.IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtencion(activity, imageUri)
        )
        sRef.putFile(imageUri)
                .addOnSuccessListener {task ->
                    Log.d("none", "=>" + task.metadata!!.reference!!.downloadUrl.toString())
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                        when(activity){
                            is UserProfileActivity -> {
                                activity.imageUploadSucess(uri.toString())
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    when(activity){
                        is UserProfileActivity -> {
                            activity.hideProgressDialog()
                        }
                    }
                }

    }


}


































package com.example.shopapp.first_youtube
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.shopapp.R
import com.example.shopapp.first_youtube.utils.Constants
import com.example.shopapp.first_youtube.utils.FirestoreClass
import com.example.shopapp.first_youtube.utils.GlideLoader
import com.example.shopapp.first_youtube.utils.User
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException
class UserProfileActivity : BaseActivity(), View.OnClickListener {
    var user = User()
    private var selectedImageUri: Uri? = null
    private var userProfileImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        if(intent.hasExtra(Constants.USER_DETAIL)){
            user = intent.getParcelableExtra(Constants.USER_DETAIL)!!
            company_user.setText(user.company)
            user_name_detail.setText(user.name)
            tlf_user_edit.setText(user.tlf)
        }
        tv_user_photo.setOnClickListener (this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.tv_user_photo -> {
                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
                        Constants.showImageChooser(this)
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_STORAGE_CODE)
                    }
                }
                R.id.btn_submit -> {
                    if(validateUserProfileDetails()){
                        showProgressDialog(resources.getString(R.string.please_wait))
                        if(selectedImageUri != null){
                            FirestoreClass().uploadImagetoCloudStorage(this, selectedImageUri!!)
                        } else {
                            updateUserDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserDetails(){
        val userHashMap = HashMap<String, Any>()
        userHashMap[Constants.COMPANY] = company_user.text.toString()
        userHashMap[Constants.NAME] = user_name_detail.text.toString()
        userHashMap[Constants.TLF] = tlf_user_edit.text.toString()
        userHashMap[Constants.PROFILECOMPLETED] = 0
        if(userProfileImageUrl.isNotEmpty()){
            userHashMap[Constants.IMAGE] = userProfileImageUrl
        }
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().updateUserProfileData(this, userHashMap)
    }

    fun userUpdateSucess(){
        hideProgressDialog()
        Toast.makeText(this, resources.getString(R.string.data_updated), Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.READ_STORAGE_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showErrorSnackBar(resources.getString(R.string.you_are_have_permission_to_read), false)
                Constants.showImageChooser(this)
            } else {
                showErrorSnackBar(resources.getString(R.string.you_dont_have), true)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if(data != null){
                    try {
                        selectedImageUri = data.data!!
                        // tv_user_photo.setImageURI(selectedImageFileUri)
                        GlideLoader(this).loadPicture(selectedImageUri!!, tv_user_photo)
                    } catch (ex: IOException){
                        showErrorSnackBar(ex.message.toString(), true)
                    }
                }
            }
        }
    }

    private fun validateUserProfileDetails(): Boolean{
        return when{
            TextUtils.isEmpty(company_user.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_company), true); false
            }
            TextUtils.isEmpty(user_name_detail.text.toString().trim{it <= ' '}) || user_name_detail.length() <= 3-> {
                showErrorSnackBar(resources.getString(R.string.err_tlf), true); false
            }
            TextUtils.isEmpty(tlf_user_edit.text.toString().trim{it <= ' '}) || tlf_user_edit.length() <= 3-> {
                showErrorSnackBar(resources.getString(R.string.err_name), true); false
            }
            else -> {showErrorSnackBar(resources.getString(R.string.try_save_database), false ); true}
        }
    }

    fun imageUploadSucess(imageUrl: String){
        hideProgressDialog()
        userProfileImageUrl = imageUrl
        updateUserDetails()
    }


}































package com.example.shopapp.first_youtube
import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: Dialog

    fun showErrorSnackBar(message: String, errorMessage: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        if(errorMessage) snackBarView.setBackgroundColor(Color.RED)
        else snackBarView.setBackgroundColor(Color.GREEN)
        snackBar.show()
    }

    fun showProgressDialog(text: String){
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.tv_progress_text.text = text
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    fun hideProgressDialog(){
        progressDialog.dismiss()
    }


}
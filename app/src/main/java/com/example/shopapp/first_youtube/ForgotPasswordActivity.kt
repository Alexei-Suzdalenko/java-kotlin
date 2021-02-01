package com.example.shopapp.first_youtube
import android.os.Bundle
import android.widget.Toast
import com.example.shopapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setActionBar()
        btn_forgot.setOnClickListener {
            val email =  email_forgot.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.email_empty), true)
            } else{
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if(task.isSuccessful){
                            Toast.makeText(this, resources.getString(R.string.email_sent), Toast.LENGTH_LONG).show()
                        } else{
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }
    }

    private fun setActionBar(){
        setSupportActionBar(toolbar_forgot_password)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back)
        }
        toolbar_forgot_password.setNavigationOnClickListener { onBackPressed() }
    }
}
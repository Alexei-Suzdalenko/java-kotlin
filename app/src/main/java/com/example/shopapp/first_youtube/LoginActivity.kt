package com.example.shopapp.first_youtube
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.shopapp.R
import com.example.shopapp.first_youtube.utils.Constants
import com.example.shopapp.first_youtube.utils.FirestoreClass
import com.example.shopapp.first_youtube.utils.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email" , "email")
        val password = sharedPreferences.getString("password", "password")
        if(email != "email" && password != "password"){
            et_email.setText(email); et_password.setText(password)
        }

        tv_forgot_password.setOnClickListener(this); btn_login_page.setOnClickListener(this); tv_register.setOnClickListener(this)

    }

    override fun onClick(view: View?){
        if(view != null){
            when(view.id){
                R.id.tv_forgot_password -> {startActivity(Intent(this, ForgotPasswordActivity::class.java))}
                R.id.btn_login_page -> {loginRegisterUser()}
                R.id.tv_register -> {startActivity(Intent(this, RegisterActivity::class.java))}
            }
        }
    }

    private fun validateLoginDetail(): Boolean{
        return when{
            TextUtils.isEmpty(et_email.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_email), true); false
            }
            TextUtils.isEmpty(et_password.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_password), true); false
            }
            else -> true
        }
    }

    private fun loginRegisterUser(){
        if(validateLoginDetail()){ showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = et_email.text.toString().trim{it <= ' '}
            val password: String = et_password.text.toString().trim{it <= ' '}
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        showErrorSnackBar (resources.getString(R.string.you_are_logged), false)
                        FirestoreClass().getUserDetails(this)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User?){
        hideProgressDialog()
        Toast.makeText(this, user!!.id, Toast.LENGTH_LONG).show()
        if(user.profileCompleted == 0){
            startActivity(Intent(this, UserProfileActivity::class.java).putExtra(Constants.USER_DETAIL, user))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }






























}

// get char form https://www.webempresa.com/
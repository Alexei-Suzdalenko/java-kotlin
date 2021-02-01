package com.example.shopapp.first_youtube
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import com.example.shopapp.R
import com.example.shopapp.first_youtube.utils.Constants
import com.example.shopapp.first_youtube.utils.FirestoreClass
import com.example.shopapp.first_youtube.utils.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
class RegisterActivity : BaseActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sharedPreferences = getSharedPreferences(Constants.SHARED_TAG, Context.MODE_PRIVATE)
        setActionBar()
        tv_login.setOnClickListener {
          onBackPressed()
        }
        btn_register.setOnClickListener {
            registerUser()
        }
    }

    private fun setActionBar(){
        setSupportActionBar(toolbar_register_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back)
        }
        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean{
        return when{
            TextUtils.isEmpty(company.text.toString().trim{it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_company), true); false
            }
            TextUtils.isEmpty(your_tlf.text.toString().trim{it <= ' '}) || your_tlf.length() <= 3-> {
                showErrorSnackBar(resources.getString(R.string.err_tlf), true); false
            }
            TextUtils.isEmpty(your_name.text.toString().trim{it <= ' '}) || your_name.length() <= 3-> {
                showErrorSnackBar(resources.getString(R.string.err_name), true); false
            }
            TextUtils.isEmpty(your_email.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_email), true); false
            }
            TextUtils.isEmpty(your_password.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_password), true); false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_checked), false); false
            }
            else -> {showErrorSnackBar(resources.getString(R.string.try_save_database), false ); true}
        }
    }

    private fun registerUser(){
        if(validateRegisterDetails()){
            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = your_email.text.toString().trim{it <= ' '}
            val password: String = your_password.text.toString().trim{it <= ' '}
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val user = User(
                            firebaseUser.uid,
                            company.text.toString().trim{it <= ' '},
                            your_tlf.text.toString().trim{it <= ' '},
                            your_name.text.toString().trim{it <= ' '},
                            your_email.text.toString().trim{it <= ' '},
                            your_password.text.toString().trim{it <= ' '},
                            "",
                            0
                        )
                        FirestoreClass().registerUser(this, user)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.apply()
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), false)
                    }
                }
        }
    }

    fun userRegisterSuccess(){
        showErrorSnackBar(resources.getString(R.string.you_are_registered), true)
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java)); finish()
        finish()
    }

}
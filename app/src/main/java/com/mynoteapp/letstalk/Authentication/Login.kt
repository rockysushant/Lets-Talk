package com.mynoteapp.letstalk.Authentication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.mynoteapp.letstalk.MainActivity
import com.mynoteapp.letstalk.R

class login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnNewAccount: TextView
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        supportActionBar?.hide()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnNewAccount = findViewById(R.id.newAccount)

        mAuth = FirebaseAuth.getInstance()


        //  ALREADY ACCOUNT FIRST TIME USER

        btnNewAccount.setOnClickListener{
            val intent= Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        //LOGIN BUTTON

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email,password);

        }

    }


    private fun login(email:String, password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this,"USER NOT EXIST , OR CHECK YOUR INTERNET " , Toast.LENGTH_SHORT).show()
                }
            }

    }
}
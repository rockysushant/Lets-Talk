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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mynoteapp.letstalk.MainActivity
import com.mynoteapp.letstalk.R
import com.mynoteapp.letstalk.Modal.User

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var alreadyAccount: TextView
    private lateinit var registerBtn: Button


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        alreadyAccount = findViewById(R.id.alreadyAccount)
        registerBtn = findViewById(R.id.registerBtn)
        mAuth = FirebaseAuth.getInstance()

        mDbRef = FirebaseDatabase.getInstance().reference


        // LOGIN HERE TEXT

      alreadyAccount.setOnClickListener {
          startActivity(Intent(this, login::class.java))
      }

        // REGISTER BUTTON

        registerBtn.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signUp(name,email,password)
        }

    }
    private  fun signUp(name: String,email:String,password: String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    Toast.makeText(this,"Successfully  Register", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this,"SOME ERROR OCUURED , OR PLEASE CHECK YOUR INTERNET", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name:String,email: String, uid:String){

        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))


    }
}
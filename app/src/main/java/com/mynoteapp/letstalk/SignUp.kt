package com.mynoteapp.letstalk

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: TextView

    private lateinit var mAuth: FirebaseAuth
    //    private  var mDbRef = DatabaseReference()
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        mAuth = FirebaseAuth.getInstance()


        mDbRef = FirebaseDatabase.getInstance().reference // Initialize in onCreate

        btnSignUp.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()


            signUp(name,email,password)
        }

    }

    //LOGIC OF LOGGING USER


    private  fun signUp(name: String,email:String,password: String){     // name string or string  name kuch bhi likh sye ha lya?

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this,"SOME ERROR OCUURED", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun addUserToDatabase(name:String,email: String, uid:String){


        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))


    }
}
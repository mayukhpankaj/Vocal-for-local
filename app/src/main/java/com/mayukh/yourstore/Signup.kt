package com.mayukh.yourstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        // Initialize Firebase Auth



        btnNewAcc.setOnClickListener{

            auth = FirebaseAuth.getInstance()

            btnNewAcc.isEnabled = false

            val email = etNewEmail.text.toString()
            val pass  = etNewPass.text.toString()

            if (email.isBlank() || pass.isBlank()) {

                Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                btnNewAcc.isEnabled = true
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                btnNewAcc.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
//

                    startActivity(Intent(this,PostActivity::class.java))
                    finish()

                } else {
                    Log.e("Signup", "signUpWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }








            }




    }
}
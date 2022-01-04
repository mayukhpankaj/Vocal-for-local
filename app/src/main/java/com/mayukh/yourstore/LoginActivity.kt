package com.mayukh.yourstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.*


private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //user signin on firebase.



        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            goPostsActivity()
        }



        // onclick call Auth for firebase

        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }

            if(!isValidEmail(email)){
                Toast.makeText(this, "Wrong Email Format.", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }


            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    goPostsActivity()

                }
                else if(task.exception is FirebaseAuthEmailException){

                    Toast.makeText(this, "No such Email", Toast.LENGTH_LONG).show()
                }
                else if(task.exception is FirebaseAuthInvalidUserException){

                    Toast.makeText(this, "Incorrect email address", Toast.LENGTH_LONG).show()
                }
                else if(task.exception is FirebaseAuthInvalidCredentialsException){

                    Toast.makeText(this, "Invalid password", Toast.LENGTH_LONG).show()
                }
                else if(task.exception is FirebaseAuthRecentLoginRequiredException){

                    Toast.makeText(this, "Login Again !", Toast.LENGTH_LONG).show()
                }
                else {
                    Log.e(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener{ error ->
                Toast.makeText(this,"Internet connection $error",Toast.LENGTH_LONG)
                btnLogin.isEnabled = true;
            }


        }

        btnNewUser.setOnClickListener{

            startActivity(Intent(this,Signup::class.java))
            finish()


        }


    }

    private fun goPostsActivity() {

        Log.i(TAG, "goPostsActivity")
        val intent = Intent(this, PostActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
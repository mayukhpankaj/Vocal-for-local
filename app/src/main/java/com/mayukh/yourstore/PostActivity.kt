package com.mayukh.yourstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_post.*

private  const val EXTRA_USERNAME = "EXTRA_USERNAME"

data class UserDetails (

    val username: String? = "",
    val location: String? = ""
        )

class PostActivity : AppCompatActivity() {

    private var signedInUser: User? = null

    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    private val TAG = "PostActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)





        storageReference = FirebaseStorage.getInstance().reference
        firestoreDb = FirebaseFirestore.getInstance()

        val useruid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener { exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }


        btnSave.setOnClickListener {

            val storename = etStorename.text.toString()
            val location = etStoreLocation.text.toString()
            val about = etAbout.text.toString()



            val usernew =  UserDetails(storename,location)

            firestoreDb.collection("users").document(useruid).set(usernew);

            Toast.makeText(this,"Success ! $useruid",Toast.LENGTH_LONG).show()

        }



        btnProfile.setOnClickListener {

            val intent = Intent(this,ProfileActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(intent)


        }

        addProduct.setOnClickListener {

            startActivity(Intent(this, CreateActivity::class.java))
        }


        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            Toast.makeText(this,"LoogedOut",Toast.LENGTH_LONG).show()

            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }
}
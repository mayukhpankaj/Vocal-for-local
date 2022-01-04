package com.mayukh.yourstore

//add
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_buy.*
import org.w3c.dom.Text


data class User (
    val username: String= "",
    val location: String= ""
        )

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


class BuyActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Firebase.firestore

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)


//        val db = Firebase.firestore
//
//        db.collection("users")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//
//                    Toast.makeText(this,"${document.id} => ${document.data}",Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("BuyActivity", "Error getting documents.", exception)
//                Toast.makeText(this,"${exception}",Toast.LENGTH_LONG).show()
//            }

            val query: CollectionReference = db.collection("users")

            val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query,User::class.java)
                .setLifecycleOwner(this).build()


            val adapter = object: FirestoreRecyclerAdapter<User,UserViewHolder>(options){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

                   val view = LayoutInflater.from(this@BuyActivity).inflate(android.R.layout.simple_list_item_2,parent,false)
                    return UserViewHolder(view)
                }

                override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {

                    val userN: TextView = holder.itemView.findViewById(android.R.id.text1)
                    val loc: TextView = holder.itemView.findViewById(android.R.id.text2)

                    userN.text = model.username
                    loc.text = model.location
                }

            }

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)







    }



}
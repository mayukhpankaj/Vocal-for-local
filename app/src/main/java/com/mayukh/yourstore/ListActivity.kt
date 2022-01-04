package com.mayukh.yourstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mayukh.yourstore.models.Post
import kotlinx.android.synthetic.main.activity_list.*

private const val EXTRA_USERNAME ="EXTRA_USERNAME"

open class ListActivity : AppCompatActivity() {



    private lateinit var  firestoreDb: FirebaseFirestore

    //layout for single list item - done
    //data  source
    //adapter
    //bind the adapter and layout manager to RV


    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostsAdapter
    private var signedInUser: User? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)




        posts = mutableListOf()
        adapter = PostsAdapter(this,posts)

        adapter.setOnItemClickListener(object: PostsAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {

                Toast.makeText(this@ListActivity,"youClicked $position",Toast.LENGTH_LONG)
                Log.i("itemclick","you clicked$position")

                val intent = Intent(this@ListActivity,InfoActivity::class.java)

                intent.putExtra("ITEM","$position");

                intent.putExtra("img","${posts[position].imageUrl}");
                intent.putExtra("desc","${posts[position].description}");
                intent.putExtra("user","${posts[position].user?.username}");
                intent.putExtra("loc","${posts[position].user?.location}");

                startActivity(intent)



            }
        })

        rvPosts.adapter = adapter
        rvPosts.layoutManager = LinearLayoutManager(this)

        firestoreDb = FirebaseFirestore.getInstance()

        //checking for null

        var postsReference = firestoreDb
            .collection("posts")
            .limit(20)
            .orderBy("creationTime",Query.Direction.DESCENDING)

        if(signedInUser!=null){

            firestoreDb.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid as String)
                .get()
                .addOnSuccessListener { userSnapshot ->
                    signedInUser = userSnapshot.toObject(User::class.java)

                }
                .addOnFailureListener{exception ->
                    Log.i("ListActivity","err $signedInUser")}




            var postsReference = firestoreDb
                .collection("posts")
                .limit(20)
                .orderBy("creationTime",Query.Direction.DESCENDING)

            val username = intent.getStringExtra(EXTRA_USERNAME)
            if(username!=null){
                supportActionBar?.title = username
                postsReference= postsReference.whereEqualTo("user.username",username)

            }


        }





        postsReference.addSnapshotListener{snapshot, exception ->

            if(exception !=null || snapshot == null){
                Toast.makeText(this,"${exception}",Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }

            val postList = snapshot.toObjects(Post::class.java)
            posts.clear()
            posts.addAll(postList)
            adapter.notifyDataSetChanged()

            for (post in postList){
                Log.i("ListActivity","Document ${post}")
//                Toast.makeText(this,"Document ${document.id}: ${document.data}",Toast.LENGTH_LONG).show()
            }



        }

    }
}
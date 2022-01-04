package com.mayukh.yourstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.item_post.view.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        val img = intent.getStringExtra("img")
        val desc = intent.getStringExtra("desc")
        val user = intent.getStringExtra("user")
        val loc = intent.getStringExtra("loc")

        textView6.text = desc;
        textView7.text = user+" ,";
        textView8.text = loc;

        Glide.with(this).load(img).into(imageView4)


    }
}


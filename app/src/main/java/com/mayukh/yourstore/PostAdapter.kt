package com.mayukh.yourstore

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mayukh.yourstore.models.Post
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_post.view.*
import java.math.BigInteger
import java.security.MessageDigest

class PostsAdapter (val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    //item onclick

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    inner class ViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            val username = post.user?.username as String
            val location = post.user?.location as String
            itemView.tvUsername.text = username
            itemView.tvLocation.text = location
            itemView.tvDescription.text = post.description;
            Glide.with(context).load(post.imageUrl).into(itemView.ivPost)
        }

        init {

            itemView.setOnClickListener{

                listener.onItemClick(absoluteAdapterPosition)
            }
        }


    }
}

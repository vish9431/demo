package com.example.pip_demo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pip_demo.R
import com.example.pip_demo.model.Post
import com.example.pip_demo.model.PostX

class PostAdapter(private val posts: List<PostX>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)
        val tagsTextView: TextView = itemView.findViewById(R.id.tagsTextView)
        val reactionsTextView: TextView = itemView.findViewById(R.id.reactionsTextView)
        val viewsTextView: TextView = itemView.findViewById(R.id.viewsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.titleTextView.text = post.title
        holder.bodyTextView.text = post.body
        holder.tagsTextView.text = "Tags: ${post.tags.joinToString(", ")}"
        holder.reactionsTextView.text =
            "Likes: ${post.reactions.likes}, Dislikes: ${post.reactions.dislikes}"
        holder.viewsTextView.text = "Views: ${post.views}"
    }

    override fun getItemCount(): Int = posts.size
}
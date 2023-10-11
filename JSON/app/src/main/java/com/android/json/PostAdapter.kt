package com.android.json

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(private var products : MutableList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){

    inner class PostViewHolder(view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
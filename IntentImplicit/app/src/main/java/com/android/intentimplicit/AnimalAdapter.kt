package com.android.intentimplicit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AnimalAdapter(
    var listImageResource: List<Int>,
    private var onClickListener: OnAnimalClickListener? = null
) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(view: View) : ViewHolder(view) {

        private var imageView = view.findViewById<ImageView>(R.id.image_item)

        init {
           imageView.setOnClickListener{
               onClickListener?.onClick(adapterPosition)
           }
        }

        fun bind(resourceImage: Int?) {
            resourceImage ?: return
            imageView.setImageResource(resourceImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_image_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listImageResource.size
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(listImageResource.getOrNull(position))
    }

    fun setOnClickListener(onClickListener: OnAnimalClickListener) {
        this.onClickListener = onClickListener
    }
}

interface OnAnimalClickListener {
    fun onClick(position: Int)
}
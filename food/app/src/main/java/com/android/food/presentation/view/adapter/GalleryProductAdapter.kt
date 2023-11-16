package com.android.food.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.bumptech.glide.Glide

class GalleryProductAdapter(
     val context: Context,
    private val galleryList: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<GalleryProductAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.img_gallery_item)

        fun bind(img: String) {
            Glide.with(context)
                .load(AppConstant.BASE_URL + img)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.no_image)
                .into(this.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount() = galleryList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(galleryList[position])
    }

    fun update(data: List<String>) {
        if (galleryList.size > 0) {
            galleryList.clear()
        }
        galleryList.addAll(data)
        notifyDataSetChanged()
    }
}
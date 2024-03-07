package com.android.my_app_music.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Album
import com.android.my_app_music.utils.OnClickItem
import com.squareup.picasso.Picasso

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    private val listAlbums: MutableList<Album> = mutableListOf()
    private var onClickItem: OnClickItem? = null

    fun updateData(lists: MutableList<Album>) {
        if (listAlbums.size > 0) {
            listAlbums.clear()
        }
        listAlbums.addAll(lists)
        notifyDataSetChanged()
    }

    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgAlbum: ImageView = view.findViewById(R.id.img_album)
        private val tvAlbumTitle: TextView = view.findViewById(R.id.tv_album_title)
        private val tvSingerName: TextView = view.findViewById(R.id.tv_album_singer_name)
        private val btnAlbums  : LinearLayout = view.findViewById(R.id.linear_album)

        fun bind(album: Album) {
            Picasso.get().load(album.albumImage).into(imgAlbum)
            tvAlbumTitle.text = album.albumName
            tvSingerName.text = album.singerName
        }

        init {
            btnAlbums.setOnClickListener {
                onClickItem?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_album, parent, false)
        return AlbumViewHolder((view))
    }

    override fun getItemCount(): Int = listAlbums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(listAlbums[position])
    }

    fun onClickItem(onClickItem: OnClickItem?) {
        this.onClickItem = onClickItem
    }
}
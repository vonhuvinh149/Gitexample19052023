package com.android.my_app_music.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Playlist
import com.android.my_app_music.utils.OnClickItem
import com.squareup.picasso.Picasso

class PlaylistAdapter : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    private var playlists: MutableList<Playlist> = mutableListOf()
    private var onClickItem: OnClickItem? = null

    fun updateData(lists: MutableList<Playlist>) {
        if (playlists.size > 0) {
            playlists.clear()
        }
        playlists.addAll(lists)
        notifyDataSetChanged()
    }

    inner class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_item_playlist)
        private val imgBackground: ImageView = view.findViewById(R.id.img_background_item_playlist)
        private val imgPlaylist: ImageView = view.findViewById(R.id.img_item_playlist)
        private val btnPlaylist: RelativeLayout = view.findViewById(R.id.relative_playlist)

        fun bind(playlist: Playlist) {
            tvTitle.text = playlist.title
            Picasso.get().load(playlist.image).into(imgPlaylist)
            Picasso.get().load(playlist.backgroundImage).into(imgBackground)
        }

        init {
            btnPlaylist.setOnClickListener {
                onClickItem?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun getItemCount() = playlists.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }

    fun onClickItem(onClickItem: OnClickItem?) {
        this.onClickItem = onClickItem
    }
}
package com.android.my_app_music.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.my_app_music.R
import com.android.my_app_music.data.model.Song
import com.android.my_app_music.utils.OnClickItem
import com.squareup.picasso.Picasso

class SongAdapter : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var listSongs: MutableList<Song> = mutableListOf()
    private var onClickItem : OnClickItem? = null

    fun updateData(lists: MutableList<Song>) {
        if (listSongs.size > 0) {
            listSongs.clear()
        }
        listSongs.addAll(lists)
        notifyDataSetChanged()
    }

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgSong: ImageView = view.findViewById(R.id.img_song_item)
        private val tvSongName: TextView = view.findViewById(R.id.tv_song_name_item)
        private val tvSinger: TextView = view.findViewById(R.id.tv_singer_item)
        private val icPlay : ImageView = view.findViewById(R.id.ic_play_song_item)
        private var btnSongItem : RelativeLayout = view.findViewById(R.id.relative_song_item)

        fun bind(song: Song) {
            tvSongName.text = song.songName
            tvSinger.text = song.singerName
            Picasso.get().load(song.imageSong).into(imgSong)
        }

        init {
            btnSongItem.setOnClickListener {
                onClickItem?.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_list_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(listSongs[position])
    }

    fun onClickItem(onClickItem: OnClickItem){
        this.onClickItem = onClickItem
    }
}
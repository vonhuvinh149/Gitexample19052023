package com.android.service_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SongAdapter() : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private val listSong: MutableList<Song> = mutableListOf()
    private var onItemSong: OnClickItemSong? = null

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgSong: ImageView = view.findViewById(R.id.img_song)
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvSingerName: TextView = view.findViewById(R.id.tv_singer)
        private val songItem: LinearLayout = view.findViewById(R.id.song_item)

        init {
            songItem.setOnClickListener {
                onItemSong?.onClick(adapterPosition)
            }
        }


        fun bind(song: Song) {
            tvTitle.text = song.title
            tvSingerName.text = song.singerName
            Picasso.get()
                .load(song.imgSong)
                .error(R.drawable.no_img)
                .placeholder(R.drawable.no_img)
                .into(imgSong)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listSong.size
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(listSong[position])
    }

    fun updateListSong(list: MutableList<Song>) {
        if (listSong.size > 0) {
            listSong.clear()
        }
        listSong.addAll(list)
    }

    fun setOnClickItemSong(onItemSong: OnClickItemSong?) {
        this.onItemSong = onItemSong
    }

    interface OnClickItemSong {
        fun onClick(position: Int)
    }
}



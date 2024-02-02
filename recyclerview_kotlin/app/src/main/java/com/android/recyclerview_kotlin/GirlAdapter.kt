package com.android.recyclerview_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GirlAdapter(private val lists: MutableList<Girl>  , val onClickItem: OnClickItem ) :
    RecyclerView.Adapter<GirlAdapter.GirlViewHolder>() {

    inner class GirlViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val img: ImageView = view.findViewById(R.id.tmg_item)
        private val tvName: TextView = view.findViewById(R.id.tv_item)

        fun bind(girl: Girl) {
            img.setImageResource(girl.img)
            tvName.text = girl.name

            this.itemView.setOnClickListener {
                onClickItem.onClickItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_girl_item, parent, false)
        return GirlViewHolder(view)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
        holder.bind(lists[position])
    }
}
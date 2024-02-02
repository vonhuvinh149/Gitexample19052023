package com.android.recyclerview

import android.content.Context
import android.text.Layout
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    val context: Context
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var listCategory: MutableList<Category> = mutableListOf()

    fun setData(lists: MutableList<Category>) {
        if (listCategory.size > 0) {
            listCategory.clear()
        }
        listCategory.addAll(lists)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val girlAdapter: GirlAdapter = GirlAdapter()

//        var tvCategory: TextView = view.findViewById(R.id.tv_category)
        var recyclerView: RecyclerView? = null



        fun onBind(category: Category) {
//            tvCategory.text = category.nameCategory ?: ""
            girlAdapter.setData(category.list)
            val linearLayoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_girl, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = listCategory.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.onBind(listCategory[position])

    }
}
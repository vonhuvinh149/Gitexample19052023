package com.android.mvvm.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.data.database.ProductEntity

class ProductAdapter(
    private val context: Context,
    private var products: MutableList<ProductEntity>
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val tvDesc: TextView = view.findViewById(R.id.tv_desc)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)
        private val image: ImageView = view.findViewById(R.id.img_product)
        private val btnDelete: ImageView = view.findViewById(R.id.btn_delete)

        fun bind(product: ProductEntity) {
            tvName.text = product.name
            tvDesc.text = product.description
            tvPrice.text = product.price.toString()
            image.setImageBitmap(product.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }
}
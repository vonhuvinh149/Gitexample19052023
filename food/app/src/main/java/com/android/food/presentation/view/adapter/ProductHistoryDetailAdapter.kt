package com.android.food.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.data.api.model.Product
import com.android.food.utils.StringUtils

class ProductHistoryDetailAdapter(
    private val products: MutableList<Product>
) : RecyclerView.Adapter<ProductHistoryDetailAdapter.ProductHistoryViewHolder>() {
    inner class ProductHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_name_product_history)
        private val tvQuantity: TextView = view.findViewById(R.id.tv_quantity_product_history)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price_product_history)
        private val tvTotalPriceProduct: TextView = view.findViewById(R.id.tv_price_product_total)
        fun bind(product: Product) {
            tvName.text = product.name
            tvQuantity.text = product.quantity.toString()
            tvPrice.text = product.price.toInt().toString()
            tvTotalPriceProduct.text =
                StringUtils.formatCurrency((product.price * product.quantity).toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.layout_product_item_history_order,
            parent,
            false
        )
        return ProductHistoryViewHolder(view)
    }

    override fun getItemCount() = products.size
    override fun onBindViewHolder(holder: ProductHistoryViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun updateListProduct(data: List<Product>?) {
        if (products.size > 0) {
            products.clear()
        }
        products.addAll(data ?: mutableListOf())
        notifyDataSetChanged()
    }
}
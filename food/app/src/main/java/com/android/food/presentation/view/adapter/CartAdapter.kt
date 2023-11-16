package com.android.food.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.data.api.model.Product
import com.android.food.utils.StringUtils
import com.bumptech.glide.Glide

class CartAdapter(
    private val context: Context,
) : Adapter<CartAdapter.CartViewHolder>() {

    private val listCartItems: MutableList<Product> = mutableListOf()
    private var onItemClickDownQuantity: OnItemClickProduct? = null
    private var onItemClickUpQuantity: OnItemClickProduct? = null

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName: TextView = view.findViewById(R.id.tv_name_cart_item)
        private val imgCartItem: ImageView = view.findViewById(R.id.img_cart_item)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price_cart_item)
        private val tvQuantity: TextView = view.findViewById(R.id.tv_quantity_cart_item)
        private val btnDown: ImageView = view.findViewById(R.id.btn_down)
        private val btnUp: ImageView = view.findViewById(R.id.btn_up)

        init {
            btnDown.setOnClickListener {
                onItemClickDownQuantity?.onClick(adapterPosition)
            }
            btnUp.setOnClickListener {
                onItemClickUpQuantity?.onClick(adapterPosition)
            }
        }

        fun bind(context: Context, product: Product) {
            tvName.text = product.name
            tvQuantity.text = product.quantity.toString()
            tvPrice.text = String.format(
                "Giá: %s VND",
                StringUtils.formatCurrency(product.price.toInt())
            )
            Glide.with(context)
                .load("${AppConstant.BASE_URL}${product.image}")
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.no_image)
                .into(imgCartItem)
        }
    }

    fun getListCartItem(): List<Product> {
        return listCartItems
    }

    fun updateAdapter(data: List<Product>?) {
        if (listCartItems.isNotEmpty()) {
            listCartItems.clear()
        }
        listCartItems.addAll(data ?: mutableListOf())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.layout_cart_item,
            parent,
            false
        )
        return CartViewHolder(view)
    }

    override fun getItemCount() = listCartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(context, listCartItems[position])
    }

    fun setOnClickDownQuantity(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickDownQuantity = onItemClickProduct
    }

    fun setOnClickUpQuantity(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickUpQuantity = onItemClickProduct
    }

    interface OnItemClickProduct {
        fun onClick(position: Int)
    }
}
package com.android.food.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.data.api.model.Product
import com.android.food.utils.StringUtils
import com.bumptech.glide.Glide

class ProductAdapter(
    private var listProducts: MutableList<Product> = mutableListOf(),
    private var context: Context? = null,
) : Adapter<ProductAdapter.ProductViewHolder>() {

    private var onItemClickAddCartItem: OnItemClickProduct? = null
    private var onItemClickDetailProduct: OnItemClickProduct? = null

    fun updateListProduct(data: List<Product>?) {
        if (listProducts.size > 0) {
            listProducts.clear()
        }
        listProducts.addAll(data ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun getListProducts(): List<Product> {
        return listProducts
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var btnAdd = view.findViewById<LinearLayout>(R.id.button_add)
        private var btnDetail = view.findViewById<LinearLayout>(R.id.button_detail)
        private var textViewName = view.findViewById<TextView>(R.id.textViewName)
        private var textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)
        private var textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        private var imageView = view.findViewById<ImageView>(R.id.imageView)

        init {
            btnAdd.setOnClickListener {
                if (onItemClickAddCartItem != null) {
                    onItemClickAddCartItem?.onClick(adapterPosition)
                }
            }
            btnDetail.setOnClickListener {
                if (onItemClickAddCartItem != null) {
                    onItemClickDetailProduct?.onClick(adapterPosition)
                }
            }
        }

        fun bind(product: Product) {
            val context = context ?: return
            textViewName.text = product.name
            textViewAddress.text = product.address
            textViewPrice.text = String.format(
                "Giá: %s VND",
                StringUtils.formatCurrency(product.price.toInt())
            )
            Glide.with(context)
                .load(AppConstant.BASE_URL + product.image)
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.no_image)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.layout_product_item,
            parent,
            false
        )
        return ProductViewHolder(view)
    }

    override fun getItemCount() = listProducts.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
    }

    fun setOnAddItemClickFood(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickAddCartItem = onItemClickProduct
    }

    fun setOnClickItemDetail(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickDetailProduct = onItemClickProduct
    }

    interface OnItemClickProduct {
        fun onClick(position: Int)
    }
}
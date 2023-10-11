package com.android.product_api.presentation.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.product_api.MainActivity
import com.android.product_api.R
import com.android.product_api.data.model.Product
import com.android.product_api.presentation.view.DetailActivity
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val context: Context,
    private var products: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

//    private var onClickListener: OnClickListener? = null

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName: TextView = view.findViewById(R.id.tv_name)
        private val tvDesc: TextView = view.findViewById(R.id.tv_desc)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)
        private val image: ImageView = view.findViewById(R.id.image_view)
        val btnDetail: LinearLayout = view.findViewById(R.id.linear_layout_item)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete)


        fun bind(product: Product) {
            tvName.text = product.name
            tvDesc.text = product.description
            tvPrice.text = product.price.toString()
            Picasso.get().load(product.image).into(image)
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

        holder.btnDetail.setOnClickListener {
            onClickGoToDetail(products[position].id)
        }

        holder.btnDelete.setOnClickListener {
            onClickDelete(products[position].id)
        }
    }

//    fun setOnClickListener(onClickListener: OnClickListener) {
//        this.onClickListener = onClickListener
//    }

//    interface OnClickListener {
//        fun onClick(position: Int)
//    }

    private fun onClickGoToDetail(position: Long) {
        Log.d("AAA", position.toString())
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("position-detail", position)
        context.startActivity(intent)
    }

    private fun onClickDelete(position: Long){
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("position-delete", position)
        context.startActivity(intent)
    }

}
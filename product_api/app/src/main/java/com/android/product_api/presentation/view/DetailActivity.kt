package com.android.product_api.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.product_api.R
import com.android.product_api.common.AppResourceState
import com.android.product_api.data.api.AppConfigApi
import com.android.product_api.data.model.Product
import com.android.product_api.data.repository.ProductRepository
import com.android.product_api.presentation.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var layoutLoading: RelativeLayout? = null
    private var tvName: TextView? = null
    private var tvDesc: TextView? = null
    private var tvPrice: TextView? = null
    private var imageDetail: ImageView? = null
    private var btnUpdate: Button? = null
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvName = findViewById(R.id.tv_name_detail)
        tvDesc = findViewById(R.id.tv_desc_detail)
        tvPrice = findViewById(R.id.tv_price_detail)
        imageDetail = findViewById(R.id.img_detail)
        btnUpdate = findViewById(R.id.btn_update)

        val productRepository = ProductRepository(AppConfigApi.apiService)

        viewModel = ViewModelProvider(this@DetailActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(productRepository) as T
            }
        })[MainViewModel::class.java]

        viewModel.loadingLiveData().observe(this@DetailActivity) {
            layoutLoading?.isGone = it
        }

        // detail product

        viewModel.detailLiveData().observe(this@DetailActivity) {
            when (it) {
                is AppResourceState.Success -> {
                    product = it.data
                    tvName?.text = it.data?.name
                    tvDesc?.text = it.data?.description
                    tvPrice?.text = it.data?.price.toString()
                    Picasso.get().load(it.data?.image.toString()).into(imageDetail)
                }

                is AppResourceState.Error -> {
                    Log.d("BBB", it.message.toString())
                }
            }
        }

        val position = intent.getLongExtra("position-detail", 0L)
        if (position != 0L) {
            viewModel.findByIdProduct(position)
        }

        btnUpdate?.setOnClickListener {
            val intent = Intent(this@DetailActivity, UpdateProductActivity::class.java)
            intent.putExtra("id-detail", position)
            intent.putExtra("product-detail", product)
            startActivity(intent)
        }

    }

}

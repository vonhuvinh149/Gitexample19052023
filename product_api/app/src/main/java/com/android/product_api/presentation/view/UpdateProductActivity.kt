package com.android.product_api.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.product_api.MainActivity
import com.android.product_api.R
import com.android.product_api.common.AppResourceState
import com.android.product_api.data.api.AppConfigApi
import com.android.product_api.data.model.Product
import com.android.product_api.data.repository.ProductRepository
import com.android.product_api.presentation.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class UpdateProductActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var  layoutLoading : LinearLayout? = null

    private var edtName: EditText? = null
    private var edtDesc: EditText? = null
    private var edtPrice: EditText? = null
    private var msgName: TextView? = null
    private var msgDesc: TextView? = null
    private var msgPrice: TextView? = null
    private var btnSave: Button? = null
    private var edtImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        mapping()

        val product: Product = intent.getSerializableExtra("product-detail") as Product
        val productId: Long = intent.getLongExtra("id-detail", 0L)

        val productRepository = ProductRepository(AppConfigApi.apiService)

        edtName?.setText(product.name)
        edtDesc?.setText(product.description)
        edtPrice?.setText(product.price.toString())
        Picasso.get().load(product.image).into(edtImage)

        viewModel =
            ViewModelProvider(this@UpdateProductActivity, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(productRepository) as T
                }
            })[MainViewModel::class.java]

        viewModel.loadingLiveData().observe(this@UpdateProductActivity) {
            layoutLoading?.isGone = it
        }

        btnSave?.setOnClickListener {
            val name: String = edtName?.editableText.toString()
            val desc: String = edtDesc?.editableText.toString()
            val price: Double = edtPrice?.editableText.toString().toDouble()
            val image: String = "https://gaixinhbikini.com/wp-content/uploads/2022/09/52322319648_7ea39c2c93_o.jpg"
            val myProduct = Product(productId, name, desc, image, price)
            viewModel.updateLiveData().observe(this@UpdateProductActivity) {
                when (it) {
                    is AppResourceState.Success -> {
                        val intent = Intent(this@UpdateProductActivity, MainActivity::class.java)
                        intent.putExtra("msg", "Cập nhật thành công")
                        startActivity(intent)
                        finish()
                    }

                    is AppResourceState.Error -> {
                        Log.d("BBB", it.message.toString())
                    }
                }
            }
            viewModel.updateProductById(productId, myProduct)
        }
    }

    private fun mapping() {
        edtName = findViewById(R.id.edt_name)
        edtDesc = findViewById(R.id.edt_desc)
        edtPrice = findViewById(R.id.edt_price)
        msgName = findViewById(R.id.msg_name)
        msgDesc = findViewById(R.id.msg_desc)
        msgPrice = findViewById(R.id.msg_price)
        btnSave = findViewById(R.id.btn_save)
        edtImage = findViewById(R.id.edt_image)
    }
}
package com.android.product_api.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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

class CreateProductActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var edtName: EditText? = null
    private var edtDesc: EditText? = null
    private var edtPrice: EditText? = null
    private var msgName: TextView? = null
    private var msgDesc: TextView? = null
    private var msgPrice: TextView? = null
    private var btnSave: Button? = null
    private var edtImage: ImageView? = null
    private lateinit var msg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        mapping()

        val productRepository = ProductRepository(AppConfigApi.apiService)

        viewModel =
            ViewModelProvider(this@CreateProductActivity, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(productRepository) as T
                }
            }
            )[MainViewModel::class.java]

        btnSave?.setOnClickListener {
            val name: String = edtName?.editableText.toString()
            val desc: String = edtName?.editableText.toString()
            val price: Double = edtPrice?.editableText.toString().toDouble()
            Log.d("BBB", price.toString())
            val image: String =
                "https://vienthammydiva.vn/wp-content/uploads/2022/05/gai-xinh-trung-quoc-2-1.jpg"
            Picasso.get().load(image).into(edtImage)

            if (name.isBlank() || desc.isBlank() || price < 0) {
                return@setOnClickListener
            }
            val product = Product(0, name, desc, image, price)
            viewModel.createLiveData().observe(this@CreateProductActivity) {
                when (it) {
                    is AppResourceState.Success -> {
                        val intent = Intent(this@CreateProductActivity, MainActivity::class.java)
                        intent.putExtra("msg-create", "Thêm mới thành công")
                        startActivity(intent)
                    }

                    is AppResourceState.Error -> {
                        Log.d("BBB", it.message.toString())
                    }
                }
            }
            viewModel.createProduct(product)
        }
    }

    private fun mapping() {
        edtName = findViewById(R.id.edt_name)
        edtDesc = findViewById(R.id.edt_desc)
        edtPrice = findViewById(R.id.edt_price)
        edtImage = findViewById(R.id.edt_image)
        msgName = findViewById(R.id.msg_name)
        msgDesc = findViewById(R.id.msg_desc)
        msgPrice = findViewById(R.id.msg_price)
        btnSave = findViewById(R.id.btn_save)
    }
}
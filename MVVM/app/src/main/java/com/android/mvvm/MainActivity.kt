package com.android.mvvm

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.common.AppResource
import com.android.mvvm.data.database.ProductEntity
import com.android.mvvm.data.repository.ProductRepository
import com.android.mvvm.presentation.adapter.ProductAdapter
import com.android.mvvm.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var editName: EditText? = null
    private var editDesc: EditText? = null
    private var editPrice: EditText? = null
    private var btnCancel: Button? = null
    private var btnCreate: Button? = null
    private var image: ImageView? = null
    private var layoutLoading: RelativeLayout? = null
    private var bitmapImage: Bitmap? = null
    private var recyclerView: RecyclerView? = null
    private var productAdapter: ProductAdapter? = null
    private var products: MutableList<ProductEntity> = mutableListOf()

    private var REQUEST_CODE_CAMERA = 123


    private lateinit var mainViewModel: MainViewModel
    private lateinit var productRepository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapping()

        productRepository = ProductRepository(this@MainActivity)

        mainViewModel = ViewModelProvider(this@MainActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(productRepository) as T
            }
        })[MainViewModel::class.java]

        mainViewModel.loadingLivedata().observe(this@MainActivity) {
            layoutLoading?.isGone  = it
        }

        mainViewModel.listProductLiveData().observe(this@MainActivity) {
            when (it) {
                is AppResource.Success -> {
                    Log.d("BBB", it.data?.size.toString())
                    products = it.data as MutableList<ProductEntity>
                    productAdapter = ProductAdapter(this@MainActivity, products)
                    recyclerView?.adapter = productAdapter
                }
                is AppResource.Error -> Log.d("BBB", it.data?.size.toString())
            }
        }


        mainViewModel.insertLiveData().observe(this@MainActivity) { it ->
            when (it) {
                is AppResource.Success -> {
                    Toast.makeText(this@MainActivity, "Create Successfully !", Toast.LENGTH_SHORT)
                        .show()
                }

                is AppResource.Error -> {
                    Toast.makeText(this@MainActivity, "Create fail !", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        mainViewModel.getListProducts()

        btnCreate?.setOnClickListener {
            val name: String = editName?.text.toString().trim()
            val desc: String = editDesc?.text.toString().trim()
            val price: Double = editPrice?.text.toString().trim().toDouble()
            if (name.isNotBlank() || desc.isNotBlank() || price.isFinite() || bitmapImage == null) {
                image?.setImageResource(R.drawable.img)
                mainViewModel.insertProduct(
                    ProductEntity(null, name, desc, price, bitmapImage)
                )
                editName?.text = null
                editDesc?.text = null
                editPrice?.text = null
            } else {
                Toast.makeText(this@MainActivity, "Vui long nhap", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        btnCancel?.setOnClickListener {

        }

        image?.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CODE_CAMERA
                    )
                } else {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
            } else {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                resultActivityCamera.launch(intent)
            }
        }
    }

    private val resultActivityCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val bitmap = it.data?.extras?.get("data") as Bitmap
                bitmapImage = bitmap
                image?.setImageBitmap(bitmap)
            }
        }

    private fun mapping() {
        image = findViewById(R.id.image)
        editName = findViewById(R.id.edit_name)
        editDesc = findViewById(R.id.edit_desc)
        editPrice = findViewById(R.id.edit_price)
        btnCancel = findViewById(R.id.btn_cancel)
        btnCreate = findViewById(R.id.btn_create)
        layoutLoading = findViewById(R.id.layout_loading)
        recyclerView = findViewById(R.id.recycler_view)
    }
}
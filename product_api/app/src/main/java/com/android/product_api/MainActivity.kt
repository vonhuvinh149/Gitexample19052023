package com.android.product_api

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.product_api.common.AppResourceState
import com.android.product_api.data.api.AppConfigApi
import com.android.product_api.data.repository.ProductRepository
import com.android.product_api.presentation.adapter.ProductAdapter
import com.android.product_api.presentation.view.CreateProductActivity
import com.android.product_api.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var layoutLoading: LinearLayout? = null
    private var btnCreateActivity: ImageView? = null
    private var reloadLayout: SwipeRefreshLayout? = null

    private var productAdapter: ProductAdapter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var viewModel: MainViewModel
    private var isLoading = false // Biến để kiểm tra xem đang tải dữ liệu không
    private val visibleThreshold = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutLoading = findViewById(R.id.relative_layout_loading)
        btnCreateActivity = findViewById(R.id.btn_activity_create)
        recyclerView = findViewById(R.id.recycler_view)
        reloadLayout = findViewById(R.id.swipeRefreshLayout)

        productAdapter = ProductAdapter(this@MainActivity, emptyList())
        recyclerView?.adapter = productAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        val itemDecoration =
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        recyclerView?.addItemDecoration(itemDecoration)


//        productAdapter?.setOnClickListener(object : ProductAdapter.OnClickListener {
//            override fun onClick(position: Int) {
//                val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                intent.putExtra("position", position)
//                startActivity(intent)
//            }
//        })

        reloadLayout?.setOnRefreshListener {
            viewModel.getProduct()
            loadData()
        }

        val msgUpdate = intent.getStringExtra("msg") ?: ""
        if (msgUpdate.isNotBlank()){
            Toast.makeText(this@MainActivity, msgUpdate, Toast.LENGTH_SHORT).show()
        }

        val productRepository = ProductRepository(AppConfigApi.apiService)

        viewModel = ViewModelProvider(this@MainActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(productRepository) as T
            }
        }
        )[MainViewModel::class.java]

        viewModel.loadingLiveData().observe(this@MainActivity) {
            layoutLoading?.isGone = it
        }

        viewModel.productsLiveData().observe(this@MainActivity) {
            when (it) {
                is AppResourceState.Success -> {
                    val products = it.data ?: emptyList()
                    productAdapter = ProductAdapter(this@MainActivity, products)
                    recyclerView?.adapter = productAdapter
                }

                is AppResourceState.Error -> {
                    Log.d("BBB", it.message.toString())
                }
            }
        }

        viewModel.getProduct()

        btnCreateActivity?.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateProductActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.deleteLiveData().observe(this@MainActivity) {
            when (it) {
                is AppResourceState.Success -> {
                    Toast.makeText(this@MainActivity, it.data.toString(), Toast.LENGTH_SHORT).show()
                }

                is AppResourceState.Error -> {
                    Log.d("BBB", it.message.toString())
                }
            }
        }
        val position = intent.getLongExtra("position-delete", 0L)
        if (position != 0L) {
            viewModel.deleteProductById(position)
        }
    }

    private fun loadData() {
        // Thực hiện việc làm mới dữ liệu ở đây

        // Khi hoàn thành, thông báo rằng đã hoàn thành làm mới
        reloadLayout?.isRefreshing = false
    }


}
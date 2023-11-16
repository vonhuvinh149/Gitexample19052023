package com.android.food.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppResource
import com.android.food.presentation.view.adapter.CartAdapter
import com.android.food.presentation.viewmodel.CartViewModel
import com.android.food.presentation.viewmodel.ProductViewModel
import com.android.food.utils.StringUtils
import com.android.food.utils.ToastUtils

class CartActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var toolBar: Toolbar
    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private var totalPrice: TextView? = null
    private var btnOrder: TextView? = null
    private lateinit var cartViewModel: CartViewModel
    private lateinit var layoutLoading: LinearLayout
    private var tvMsg: TextView? = null
    private lateinit var cartID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(this@CartActivity) as T
            }
        })[ProductViewModel::class.java]

        cartViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(this@CartActivity) as T
            }
        })[CartViewModel::class.java]
        initView()
        observerData()
        event()
    }

    private fun event() {

        cartAdapter.setOnClickDownQuantity(object : CartAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                ToastUtils.showToast(this@CartActivity, "down")
                val product = cartAdapter
                    .getListCartItem()
                    .getOrNull(index = position)
                if (cartID.isNotBlank() && product != null) {
                    val quantity: Int = product.quantity - 1
                    cartViewModel.requestUpdateCart(cartID, product.id, quantity)
                }
            }
        })

        cartAdapter.setOnClickUpQuantity(object : CartAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                ToastUtils.showToast(this@CartActivity, "up")
                val product = cartAdapter
                    .getListCartItem()
                    .getOrNull(index = position)
                if (cartID.isNotBlank() && product != null) {
                    val quantity: Int = product.quantity + 1
                    cartViewModel.requestUpdateCart(cartID, product.id, quantity)
                }
            }
        })

        btnOrder?.setOnClickListener {
            if (cartID.isNotBlank()) {
                cartViewModel.requestCartConform(cartID)
            }
        }
    }

    private fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        recyclerView = findViewById(R.id.recycler_view_cart_item)
        totalPrice = findViewById(R.id.total_price)
        tvMsg = findViewById(R.id.cart_msg)
        layoutLoading = findViewById(R.id.layout_loading)
        btnOrder = findViewById(R.id.btn_order)
        cartAdapter = CartAdapter(context = this@CartActivity)
        recyclerView.adapter = cartAdapter
        recyclerView.setHasFixedSize(true)
        customToolBar()
    }

    private fun customToolBar() {
        val btnBack = R.drawable.ic_back
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Giỏ hàng"
        supportActionBar?.setHomeAsUpIndicator(btnBack)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun observerData() {

        cartViewModel.getUpdateCartLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    cartAdapter.updateAdapter(it.data?.products)
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this@CartActivity, it.message ?: "")
                }
            }
        }

        cartViewModel.getCartOrderLivedData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    cartAdapter.updateAdapter(emptyList())
                    totalPrice?.text = String.format(
                        "%s VND",
                        StringUtils.formatCurrency(0)
                    )
                    tvMsg?.text = it.message ?: ""
                }

                is AppResource.Error -> {
                    Log.d("BBB", it.message ?: "")
                }
            }
        }

//        cartViewModel.getLoadingLiveData().observe(this) {
//            layoutLoading.isGone = !it
//        }

        productViewModel.getCartLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    cartAdapter.updateAdapter(it.data?.products)
                    totalPrice?.text = String.format(
                        "%s VND",
                        StringUtils.formatCurrency(it.data?.price?.toInt() ?: 0)
                    )
                    cartID = it.data?.id.toString()
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message ?: "")
                }
            }
        }
        productViewModel.executeGetCart()
    }
}
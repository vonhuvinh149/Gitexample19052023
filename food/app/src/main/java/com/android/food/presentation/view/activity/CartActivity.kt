package com.android.food.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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
    private var btnRedirectProduct: TextView? = null
    private var tvRedirectProduct: LinearLayout? = null
    private lateinit var cartViewModel: CartViewModel
    private lateinit var layoutLoading: LinearLayout
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

        btnRedirectProduct?.setOnClickListener {
            val intent = Intent(this@CartActivity , HomeActivity::class.java)
            startActivity(intent)
        }

        cartAdapter.setOnClickDownQuantity(object : CartAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                val product = cartAdapter
                    .getListCartItem()
                    .getOrNull(index = position)
                if (product != null ) {
                    if(product.quantity > 1 ){
                        val quantity: Int = product.quantity - 1
                        cartViewModel.requestUpdateCart(cartID, product.id, quantity)
                    }
                }
            }
        })

        cartAdapter.setOnClickUpQuantity(object : CartAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                val product = cartAdapter
                    .getListCartItem()
                    .getOrNull(index = position)
                if ( product != null ) {
                    val quantity: Int = product.quantity + 1
                    cartViewModel.requestUpdateCart(cartID, product.id, quantity)
                }
            }
        })

        btnOrder?.setOnClickListener {
            cartViewModel.requestCartConform(cartID)
        }
    }

    private fun initView() {
        toolBar = findViewById(R.id.tool_bar)
        recyclerView = findViewById(R.id.recycler_view_cart_item)
        totalPrice = findViewById(R.id.total_price)
        layoutLoading = findViewById(R.id.layout_loading)
        btnOrder = findViewById(R.id.btn_order)
        btnRedirectProduct = findViewById(R.id.btn_buy_product)
        tvRedirectProduct = findViewById(R.id.redirect_product)
        cartAdapter = CartAdapter(context = this@CartActivity)
        recyclerView.adapter = cartAdapter
        recyclerView.setHasFixedSize(true)
        customToolBar()
    }

    private fun customToolBar() {
        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = "Giỏ hàng"
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
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

        cartViewModel.getLoadingLiveData().observe(this) {
            layoutLoading.isGone = !it
        }

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
                    ToastUtils.showToast(this , it.data.toString())
                    cartAdapter.updateAdapter(emptyList())
                    totalPrice?.text = String.format(
                        "%s VND",
                        StringUtils.formatCurrency(0)
                    )
                    tvRedirectProduct?.visibility = View.VISIBLE
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this , it.message ?: "")
                }
            }
        }

        productViewModel.getCartLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    if (it.data?.products?.size!! > 0) {
                        tvRedirectProduct?.visibility = View.GONE
                        cartAdapter.updateAdapter(it.data.products)
                    } else {
                        tvRedirectProduct?.visibility = View.VISIBLE
                        cartAdapter.updateAdapter(it.data.products)
                        recyclerView.visibility = View.GONE
                    }
                    totalPrice?.text = String.format(
                        "%s VND",
                        StringUtils.formatCurrency(it.data.price.toInt() ?: 0)
                    )
                    cartID = it.data.id
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message ?: "")
                }
            }
        }
        productViewModel.executeGetCart()
    }
}
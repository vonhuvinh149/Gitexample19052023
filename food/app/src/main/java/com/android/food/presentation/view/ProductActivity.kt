package com.android.food.presentation.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
import com.android.food.data.api.model.Cart
import com.android.food.presentation.view.adapter.ProductAdapter
import com.android.food.presentation.view.activity.CartActivity
import com.android.food.presentation.view.activity.ProductDetailActivity
import com.android.food.presentation.viewmodel.ProductViewModel
import com.android.food.utils.ToastUtils

class ProductActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var layoutLoading: LinearLayout
    private var cartItemArea: FrameLayout? = null
    private var textBadge: TextView? = null
    private var toolBar: Toolbar? = null
    private val appSharePreference = AppSharePreference(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productViewModel =
            ViewModelProvider(this@ProductActivity, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(this@ProductActivity) as T
                }
            })[ProductViewModel::class.java]
        initView()
        observerData()
        event()

    }

    private fun event() {

        productAdapter.setOnAddItemClickFood(object : ProductAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                val idProduct = productAdapter
                    .getListProducts()
                    .getOrNull(index = position)?.id ?: ""
                if (idProduct.isNotEmpty()) {
                    productViewModel.executeAddToCart(idProduct)
                }
            }
        })

        productAdapter.setOnClickItemDetail(object : ProductAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                val product = productAdapter
                    .getListProducts()
                    .getOrNull(index = position)
                val intent = Intent(this@ProductActivity, ProductDetailActivity::class.java)
                intent.putExtra(AppConstant.PRODUCT_DETAIL_KEY, product)
                startActivity(intent)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val token = ""
        if (token.isNullOrEmpty()) {
            menuInflater.inflate(R.menu.menu_before_product, menu)
        } else {
            menuInflater.inflate(R.menu.menu_product, menu)
            val rootView = menu?.findItem(R.id.item_menu_cart)?.actionView
            cartItemArea = rootView?.findViewById(R.id.frame_layout_cart_area)
            textBadge = rootView?.findViewById(R.id.text_cart_badge)
            cartItemArea?.setOnClickListener {
                val intent = Intent(this@ProductActivity, CartActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.item_menu_history_order -> {
//                val intent = Intent(this@ProductActivity, HistoryOrderActivity::class.java)
//                startActivity(intent)
//            }

            R.id.item_menu_profile -> {
//                val intent = Intent(this@ProductActivity, ProfileActivity::class.java)
//                startActivity(intent)
            }
        }
        return true
    }

    private fun initView() {
        productRecyclerView = findViewById(R.id.recycler_view_product)
        layoutLoading = findViewById(R.id.layout_loading)
        toolBar = findViewById(R.id.toolbar_home)
        productAdapter = ProductAdapter(context = this@ProductActivity)
        productRecyclerView.adapter = productAdapter
        productRecyclerView.setHasFixedSize(true)

        //  custom tiêu đề toolbar
        customToolbar()


    }

    private fun customToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Danh sách món ăn"
        supportActionBar?.title.let {
            val spannableString = SpannableString(it)
            if (it != null) {
                spannableString.setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    0,
                    it.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
            supportActionBar?.title = spannableString
        }
    }

    private fun observerData() {

        productViewModel.getRequestProduct()
        productViewModel.executeGetCart()

        productViewModel.getLoadingLiveData().observe(this) {
            layoutLoading.isGone = !it
        }

        productViewModel.getProductLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    productAdapter.updateListProduct(it.data)
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message ?: "")
                }

                else -> {}
            }
        }

        productViewModel.getCartLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    updateBadge(it.data ?: Cart())
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message ?: "")
                }

                else -> {}
            }
        }
    }

    private fun updateBadge(cart: Cart) {
        val totalProduct = cart.products.size
        if (totalProduct == 0) {
            textBadge?.isGone = true
        } else {
            textBadge?.isVisible = true
            textBadge?.text = cart.products
                .map { it.quantity }
                .reduce { acc, quantity -> acc + quantity }
                .toString()
        }
    }
}
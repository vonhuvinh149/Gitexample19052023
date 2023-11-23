package com.android.food.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
import com.android.food.data.api.model.Cart
import com.android.food.data.api.model.Product
import com.android.food.presentation.view.adapter.GalleryProductAdapter
import com.android.food.presentation.viewmodel.ProductViewModel
import com.android.food.utils.ProductUtils
import com.android.food.utils.StringUtils
import com.android.food.utils.ToastUtils
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private var cartItemArea: FrameLayout? = null
    private var textBadge: TextView? = null
    private var toolBar: Toolbar? = null
    private lateinit var layoutLoading: LinearLayout
    private var tvName: TextView? = null
    private var tvPrice: TextView? = null
    private var tvAddress: TextView? = null
    private var tvDate: TextView? = null
    private lateinit var imgProduct: ImageView
    private var btnAddToCart: LinearLayout? = null
    private val sharePreference = AppSharePreference(this)
    private var productLiveData = MutableLiveData<Product>()
    private lateinit var galleryProductAdapter: GalleryProductAdapter
    private lateinit var galleryRecyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        productViewModel =
            ViewModelProvider(this@ProductDetailActivity, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(this@ProductDetailActivity) as T
                }
            })[ProductViewModel::class.java]

        val product = intent.getSerializableExtra(AppConstant.PRODUCT_DETAIL_KEY) as? Product
        productLiveData.value = product

        initView()
        observerData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (sharePreference.isTokenValid()) {
            menuInflater.inflate(R.menu.menu_product, menu)
            val rootView = menu?.findItem(R.id.item_menu_cart)?.actionView
            cartItemArea = rootView?.findViewById(R.id.frame_layout_cart_area)
            textBadge = rootView?.findViewById(R.id.text_cart_badge)
            cartItemArea?.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackHome()
                return true
            }
        }
        return true
    }

    private fun onBackHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        galleryProductAdapter = GalleryProductAdapter(context = this@ProductDetailActivity)
        galleryRecyclerView = findViewById(R.id.recycler_view_gallery)
        toolBar = findViewById(R.id.toolbar_back)
        layoutLoading = findViewById(R.id.layout_loading)
        tvName = findViewById(R.id.tv_name_product)
        tvDate = findViewById(R.id.tv_date_create)
        tvPrice = findViewById(R.id.tv_price)
        tvAddress = findViewById(R.id.tv_address)
        btnAddToCart = findViewById(R.id.btn_add_to_cart)
        imgProduct = findViewById(R.id.img_detail)
        customToolBar()
    }

    private fun customToolBar() {
        val btnBack = R.drawable.ic_back
        setSupportActionBar(toolBar)
        toolBar?.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = "Chi tiết sản phẩm"
        supportActionBar?.setHomeAsUpIndicator(btnBack)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observerData() {
        productViewModel.executeGetCart()

        productViewModel.getLoadingLiveData().observe(this) {
            layoutLoading.isGone = !it
        }

        productViewModel.getCartLiveData().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    updateBadge(it.data ?: Cart())
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(this, it.message ?: "")
                }
            }
        }

        productLiveData.observe(this) {
            if (it != null) {
                tvName?.text = it.name
                tvPrice?.text = String.format(
                    "Giá: %s VND",
                    StringUtils.formatCurrency(it.price.toInt())
                )
                tvAddress?.text = "Địa chỉ: ${it.address}"
                tvDate?.text = "Ngày mở bán: ${StringUtils.formatDate(it.dateUpdated)}"
                Glide.with(this).load(AppConstant.BASE_URL + it.image)
                    .placeholder(R.drawable.ic_logo)
                    .error(R.drawable.no_image)
                    .into(imgProduct)
                event(it.id)
                galleryProductAdapter.update(it.gallery)
                galleryRecyclerView.adapter = galleryProductAdapter
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

    private fun event(id: String) {
        btnAddToCart?.setOnClickListener {
            if (sharePreference.isTokenValid()) {
                productViewModel.executeAddToCart(id)
            } else {
                ToastUtils.showToast(this@ProductDetailActivity, "vui lòng đăng nhập")
                runBlocking {
                    delay(500)
                    val intent = Intent(this@ProductDetailActivity, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
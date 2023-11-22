@file:Suppress("UNREACHABLE_CODE")

package com.android.food.presentation.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.common.AppResource
import com.android.food.common.AppSharePreference
import com.android.food.data.api.model.Cart
import com.android.food.presentation.view.activity.CartActivity
import com.android.food.presentation.view.activity.ProductDetailActivity
import com.android.food.presentation.view.activity.SignInActivity
import com.android.food.presentation.view.activity.SignInRefreshActivity
import com.android.food.presentation.view.adapter.ProductAdapter
import com.android.food.presentation.viewmodel.ProductViewModel
import com.android.food.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var layoutLoading: LinearLayout
    private var cartItemArea: FrameLayout? = null
    private var textBadge: TextView? = null
    private var toolBar: Toolbar? = null
    private lateinit var sharePreference: AppSharePreference
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view = inflater.inflate(R.layout.fragment_product, container, false)

        initView()
        observerData()
        event()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharePreference = AppSharePreference(context)

        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(context) as T
            }
        }

        )[ProductViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (sharePreference.isTokenValid()) {
            inflater.inflate(R.menu.menu_product, menu)
            val rootView = menu.findItem(R.id.item_menu_cart)?.actionView
            cartItemArea = rootView?.findViewById(R.id.frame_layout_cart_area)
            textBadge = rootView?.findViewById(R.id.text_cart_badge)
            cartItemArea?.setOnClickListener {
                if (sharePreference.isTokenExpirationTime()) {
                    val intent = Intent(context, CartActivity::class.java)
                    startActivity(intent)
                } else {
                    runBlocking {
                        ToastUtils.showToast(
                            requireActivity(),
                            "Phiên làm việc của bạn đã hết hạn. Vui lòng đăng nhập lại."
                        )
                        delay(1000)
                        val intent = Intent(context, SignInRefreshActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun observerData() {

        productViewModel.getLoadingLiveData().observe(viewLifecycleOwner) {
            layoutLoading.isGone = !it
        }

        productViewModel.getProductLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    productAdapter.updateListProduct(it.data)
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(requireActivity(), it.message ?: "")
                }
            }
        }

        productViewModel.getRequestProduct()

        productViewModel.getCartLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    updateBadge(it.data ?: Cart())
                }

                is AppResource.Error -> {
                    ToastUtils.showToast(requireActivity(), it.message ?: "")
                }

                else -> {}
            }
        }
        productViewModel.executeGetCart()

    }

    private fun event() {

        productAdapter.setOnAddItemClickFood(object : ProductAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                if (sharePreference.isTokenValid()) {
                    if (sharePreference.isTokenExpirationTime()) {
                        val idProduct = productAdapter
                            .getListProducts()
                            .getOrNull(index = position)?.id ?: ""
                        if (idProduct.isNotEmpty()) {
                            productViewModel.executeAddToCart(idProduct)
                        }
                    } else {
                        ToastUtils.showToast(
                            requireActivity(),
                            "Phiên làm việc của bạn đã hết hạn. Vui lòng đăng nhập lại."
                        )
                        runBlocking {
                            delay(1000)
                            val intent = Intent(context, SignInRefreshActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    ToastUtils.showToast(requireActivity(), "Vui lòng đăng nhập")
                    val intent = Intent(context, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        productAdapter.setOnClickItemDetail(object : ProductAdapter.OnItemClickProduct {
            override fun onClick(position: Int) {
                val product = productAdapter
                    .getListProducts()
                    .getOrNull(index = position)
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra(AppConstant.PRODUCT_DETAIL_KEY, product)
                startActivity(intent)
            }
        })
    }

    private fun customToolbar() {
        val initActivity = activity as? AppCompatActivity
        initActivity?.setSupportActionBar(toolBar)
        initActivity?.supportActionBar?.title = "Danh sách món ăn"
        toolBar?.setTitleTextColor(Color.WHITE)
        setHasOptionsMenu(true)
    }

    private fun initView() {
        layoutLoading = view.findViewById(R.id.layout_loading_fr)
        productRecyclerView = view.findViewById(R.id.recycler_view_product_fr)
        toolBar = view.findViewById(R.id.toolbar_home_fr)
        productAdapter = ProductAdapter(context = context)
        productRecyclerView.adapter = productAdapter
        productRecyclerView.setHasFixedSize(true)

        customToolbar()
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
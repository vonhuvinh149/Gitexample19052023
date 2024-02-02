package com.android.food.presentation.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.android.food.presentation.view.activity.HistoryOrderDetailActivity
import com.android.food.presentation.view.adapter.HistoryAdapter
import com.android.food.presentation.viewmodel.HistoryVIewModel
import com.android.food.presentation.viewmodel.ProductViewModel


class HistoryFragment : Fragment() {

    private lateinit var historyVIewModel: HistoryVIewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var recyclerViewHistory: RecyclerView
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

        view = inflater.inflate(R.layout.fragment_history, container, false)

        sharePreference = AppSharePreference(requireContext())

        initView()
        observerData()
        event()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        sharePreference = AppSharePreference(context)

        historyVIewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HistoryVIewModel(context) as T
            }
        })[HistoryVIewModel::class.java]

        productViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(context) as T
            }
        })[ProductViewModel::class.java]


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (sharePreference.isTokenValid()) {
            inflater.inflate(R.menu.menu_product, menu)
            val rootView = menu.findItem(R.id.item_menu_cart)?.actionView
            cartItemArea = rootView?.findViewById(R.id.frame_layout_cart_area)
            textBadge = rootView?.findViewById(R.id.text_cart_badge)
            cartItemArea?.setOnClickListener {
                val intent = Intent(context, CartActivity::class.java)
                startActivity(intent)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initView() {
        recyclerViewHistory = view.findViewById(R.id.recycler_view_history)
        layoutLoading = view.findViewById(R.id.layout_loading)
        historyAdapter = HistoryAdapter(context = context)
        recyclerViewHistory.adapter = historyAdapter
        recyclerViewHistory.setHasFixedSize(true)
        toolBar = view.findViewById(R.id.toolbar_history_fr)
        customToolbar()
    }

    private fun event() {
        historyAdapter.setOnClickItemHistory(object : HistoryAdapter.OnClickItemHistory {
            override fun onClick(position: Int) {
                val historyItem = historyAdapter
                    .getListProducts()
                    .getOrNull(index = position)
                val intent = Intent(context, HistoryOrderDetailActivity::class.java)
                intent.putExtra(AppConstant.HISTORY_ORDER_DETAIL_KEY, historyItem)
                startActivity(intent)
            }
        })
    }

    private fun observerData() {

        historyVIewModel.getLoadingLiveData().observe(viewLifecycleOwner) {
            layoutLoading.isGone = !it
        }
        historyVIewModel.getHistoryOrderLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    historyAdapter.updateListProduct(it.data)
                }

                is AppResource.Error -> {

                }
            }
        }
        productViewModel.getCartLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is AppResource.Success -> {
                    updateBadge(it.data ?: Cart())
                }

                is AppResource.Error -> {

                }
            }
        }

        productViewModel.executeGetCart()
        historyVIewModel.requestHistoryOrder()
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

    private fun customToolbar() {
        val initActivity = activity as? AppCompatActivity
        initActivity?.setSupportActionBar(toolBar)
        toolBar?.setTitleTextColor(Color.WHITE)
        initActivity?.supportActionBar?.title = "Lịch sử mua hàng"
        setHasOptionsMenu(true)
    }
}
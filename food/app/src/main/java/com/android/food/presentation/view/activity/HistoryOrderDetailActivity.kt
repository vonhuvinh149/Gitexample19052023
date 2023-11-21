package com.android.food.presentation.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.data.api.model.HistoryOrder
import com.android.food.presentation.view.adapter.ProductHistoryDetailAdapter
import com.android.food.utils.StringUtils

class HistoryOrderDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productHistoryAdapter: ProductHistoryDetailAdapter
    private var tvDateTime: TextView? = null
    private var tvTotalPrice: TextView? = null
    private var toolbar: Toolbar? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order_detail)

        val historyOrder =
            intent.getSerializableExtra(AppConstant.HISTORY_ORDER_DETAIL_KEY) as? HistoryOrder

        initView()

        if (historyOrder != null) {
            tvDateTime?.text = StringUtils.formatDateTime(historyOrder.dateCreated)
            tvTotalPrice?.text = String.format(
                "Tổng tiền đơn hàng: %s VND",
                StringUtils.formatCurrency(historyOrder.price.toInt())
            )
            productHistoryAdapter.updateListProduct(historyOrder.products)
            recyclerView.adapter = productHistoryAdapter
        }
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
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        finish()
    }

    private fun customToolbar() {
        setSupportActionBar(toolbar)
        toolbar?.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = ""
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        productHistoryAdapter = ProductHistoryDetailAdapter(mutableListOf())
        recyclerView = findViewById(R.id.recycler_view_product_history)
        tvDateTime = findViewById(R.id.tv_date_time_history_item)
        tvTotalPrice = findViewById(R.id.total_price)
        toolbar = findViewById(R.id.tool_bar)
        customToolbar()
    }

}
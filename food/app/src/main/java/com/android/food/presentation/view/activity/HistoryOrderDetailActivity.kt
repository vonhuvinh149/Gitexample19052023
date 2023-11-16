package com.android.food.presentation.view.activity

import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.data.api.model.HistoryOrder
import com.android.food.data.api.model.Product
import com.android.food.presentation.view.adapter.ProductHistoryAdapter
import com.android.food.utils.StringUtils

class HistoryOrderDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productHistoryAdapter: ProductHistoryAdapter
    private var tvDateTime: TextView? = null
    private var tvTotalPrice: TextView? = null

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
                "Tổng tiền đơn hàng: %s VND" , StringUtils.formatCurrency(historyOrder.price.toInt())
            )
            productHistoryAdapter.updateListProduct(historyOrder.products)
            recyclerView.adapter = productHistoryAdapter
        }

    }

    private fun initView() {
        productHistoryAdapter = ProductHistoryAdapter(mutableListOf())
        recyclerView = findViewById(R.id.recycler_view_product_history)
        tvDateTime = findViewById(R.id.tv_date_time_history_item)
        tvTotalPrice = findViewById(R.id.total_price)
    }

}
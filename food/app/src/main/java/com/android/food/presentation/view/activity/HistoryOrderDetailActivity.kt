package com.android.food.presentation.view.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.android.food.R
import com.android.food.common.AppConstant
import com.android.food.data.api.model.HistoryOrder
import com.android.food.data.api.model.Product

class HistoryOrderDetailActivity : AppCompatActivity() {

    private var tableLayout: TableLayout? = null
    private var tvName : TextView? = null
    private var tvQuantity : TextView? = null
    private var tvPrice : TextView? = null
    private var tvTotalPriceProduct : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order_detail)


        initView()

        val historyOrder =
            intent.getSerializableExtra(AppConstant.HISTORY_ORDER_DETAIL_KEY) as? HistoryOrder

    }

    private fun initView() {

    }


}
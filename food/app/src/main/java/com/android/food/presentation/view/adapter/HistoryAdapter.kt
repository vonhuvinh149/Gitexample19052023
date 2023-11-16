package com.android.food.presentation.view.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.android.food.R
import com.android.food.data.api.model.HistoryOrder
import com.android.food.utils.StringUtils

class HistoryAdapter(
    private var listHistory: MutableList<HistoryOrder> = mutableListOf(),
    private var context: Context? = null,
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var onItemClickHistoryOrder: OnClickItemHistory? = null

    fun updateListProduct(data: List<HistoryOrder>?) {
        if (listHistory.size > 0) {
            listHistory.clear()
        }
        listHistory.addAll(data ?: mutableListOf())
        notifyDataSetChanged()
    }

    fun getListProducts(): List<HistoryOrder> {
        return listHistory
    }

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvCount: TextView = view.findViewById(R.id.tv_count)
        private val tvDate: TextView = view.findViewById(R.id.tv_date)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)
        private val itemHistory: LinearLayout = view.findViewById(R.id.item_history)

        init {
            itemHistory.setOnClickListener {
                if (onItemClickHistoryOrder != null) {
                    onItemClickHistoryOrder?.onClick(adapterPosition)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(historyOrder: HistoryOrder, count: Int) {
            tvCount.text = count.toString()
            tvDate.text = StringUtils.formatDateTime(historyOrder.dateCreated)
            tvPrice.text = StringUtils.formatCurrency(historyOrder.price.toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount() = listHistory.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val count = position + 1
        holder.bind(listHistory[position], count)
    }

    fun setOnClickItemHistory(onClickItemHistory: OnClickItemHistory) {
        this.onItemClickHistoryOrder = onClickItemHistory
    }

    interface OnClickItemHistory {
        fun onClick(position: Int)
    }
}
package com.android.content_provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneAdapter(
    private val phones: MutableList<PhoneBook>
) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {


    fun updateData(list: MutableList<PhoneBook>) {
        if (phones.size > 0) {
            phones.clear()
        }
        phones.addAll(list)
        notifyDataSetChanged()
    }

    inner class PhoneViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName: TextView = view.findViewById(R.id.name_user)
        private val tvPhone: TextView = view.findViewById(R.id.phone_number)


        fun bind(phoneBook: PhoneBook) {
            tvName.text = phoneBook.name
            tvPhone.text = phoneBook.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_item_phone, parent, false)
        return PhoneViewHolder(view)
    }

    override fun getItemCount() = phones.size

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phones[position])
    }
}
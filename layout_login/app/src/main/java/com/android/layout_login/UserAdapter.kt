package com.android.layout_login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    var context: Context,
    var listUser: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtUserName: TextView = view.findViewById(R.id.displayUserName)
        private var txtDesc: TextView = view.findViewById(R.id.displayDesc)
        private var image: ImageView = view.findViewById(R.id.imageUser)

        fun bind(user: User) {
            txtUserName.text = user.name
            txtDesc.text = user.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.activity_line_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}
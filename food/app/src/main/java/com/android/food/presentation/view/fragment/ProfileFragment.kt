package com.android.food.presentation.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.android.food.R
import com.android.food.common.AppSharePreference
import com.android.food.data.api.model.User
import com.android.food.presentation.view.activity.HomeActivity
import com.android.food.presentation.view.activity.SignInActivity
import com.android.food.utils.StringUtils
import com.android.food.utils.ToastUtils

class ProfileFragment(private val context: Context) : Fragment() {

    private var imgAvatar: ImageView? = null
    private var tvName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvPhone: TextView? = null
    private var tvJoinDate: TextView? = null
    private var btnLoginOrLogout: TextView? = null
    private val sharePreference = AppSharePreference(context)
    private var myUser = User()

    private var btnUpdateInfo: TextView? = null
    private lateinit var contentInformation: LinearLayout

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        myUser = sharePreference.getUser() ?: User()

        initView(view)
        event()
        assignData()
        return view
    }

    private fun event() {
        btnLoginOrLogout?.setOnClickListener {
            if (myUser.token.isNotBlank()) {
                sharePreference.logout()
                ToastUtils.showToast(context, "Đăng Xuất Thành Công")
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun assignData() {
        if (myUser.registerDate.isNotBlank()) {
            tvName?.text = myUser.name
            tvEmail?.text = myUser.email
            tvPhone?.text = myUser.phone
            tvJoinDate?.text = StringUtils.formatDate(myUser.registerDate)
            imgAvatar?.setImageResource(R.drawable.img_avatar)
            btnLoginOrLogout?.text = "Đăng Xuất"
            contentInformation.visibility = View.VISIBLE
            btnUpdateInfo?.visibility = View.VISIBLE
        } else {
            btnLoginOrLogout?.text = "Đăng Nhập"
            contentInformation.visibility = View.GONE
            btnUpdateInfo?.visibility = View.GONE
            tvName?.visibility = View.GONE
            imgAvatar?.setImageResource(R.drawable.no_image)
        }
    }

    private fun initView(view: View) {
        imgAvatar = view.findViewById(R.id.img_info_avatar)
        tvName = view.findViewById(R.id.tv_user_name)
        tvEmail = view.findViewById(R.id.tv_email_profile)
        tvPhone = view.findViewById(R.id.tv_phone_profile)
        tvJoinDate = view.findViewById(R.id.tv_date_join)
        btnLoginOrLogout = view.findViewById(R.id.btn_logout_or_login)
        contentInformation = view.findViewById(R.id.information)
        btnUpdateInfo = view.findViewById(R.id.btn_update_information)
    }

}


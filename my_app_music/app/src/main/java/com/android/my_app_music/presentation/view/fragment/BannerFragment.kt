package com.android.my_app_music.presentation.view.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.android.my_app_music.R
import com.android.my_app_music.common.AppResource
import com.android.my_app_music.data.model.Advertisement
import com.android.my_app_music.presentation.view.adapter.BannerAdapter
import com.android.my_app_music.presentation.viewmodel.AdvertisementViewModel
import me.relex.circleindicator.CircleIndicator
import kotlin.properties.Delegates


class BannerFragment : Fragment() {

    private lateinit var view: View
    private var viewPager: ViewPager? = null
    private var indicator: CircleIndicator? = null
    private lateinit var adsViewModel: AdvertisementViewModel
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private var currentItem: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_banner, container, false)

        initView()
        observer()

        return view
    }

    private fun observer() {
        adsViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AdvertisementViewModel() as T
            }
        })[AdvertisementViewModel::class.java]

        adsViewModel.getAdsLiveData().observe(requireActivity()) {
            when (it) {
                is AppResource.Success -> {
                    bannerAdapter = BannerAdapter(requireContext(), it.data ?: mutableListOf())
                    viewPager?.adapter = bannerAdapter
                    indicator?.setViewPager(viewPager)
                    handler = Handler()
                    runnable = Runnable {
                        currentItem = viewPager?.currentItem ?: 0
                        currentItem++
                        if (currentItem >= (viewPager?.adapter as BannerAdapter).count) {
                            currentItem = 0
                        }
                        viewPager?.setCurrentItem(currentItem, true)
                        handler.postDelayed(runnable, 4000)
                    }
                    handler.postDelayed(runnable, 4000)

                }

                is AppResource.Error -> {

                }
            }
        }

        adsViewModel.executeData()
    }

    private fun initView() {
        viewPager = view.findViewById(R.id.view_pager)
        indicator = view.findViewById(R.id.indicator)
        adsViewModel = AdvertisementViewModel()
    }


}
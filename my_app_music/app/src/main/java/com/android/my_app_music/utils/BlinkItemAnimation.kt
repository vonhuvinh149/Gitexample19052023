package com.android.my_app_music.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class BlinkItemAnimation : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) {
            val animator = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)
            animator.duration = 500
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    dispatchAddFinished(holder)
                }
            })
            animator.start()
            return true
        }
        return super.animateAdd(holder)
    }
}
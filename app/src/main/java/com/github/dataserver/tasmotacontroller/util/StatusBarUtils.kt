package com.github.dataserver.tasmotacontroller.util

import android.app.Activity
import android.view.WindowManager
import com.google.android.material.elevation.SurfaceColors

object StatusBarUtils {
    fun setStatusBarColor(activity: Activity) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val statusBarColor =
            SurfaceColors.SURFACE_2.getColor(activity) // Replace with your color logic
        window.statusBarColor = statusBarColor
    }
}


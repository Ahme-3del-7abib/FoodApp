package com.simplx.apps.ysolutiontask.utils

import android.app.Activity
import android.content.Context
import android.content.Context.*
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.showToastMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

object FoodUtils {

    fun isNetworkConnected(activity: Activity): Boolean {
        val cm =
            activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }
}
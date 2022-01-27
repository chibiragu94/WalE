package com.chibi.waie.ui.base

import android.net.ConnectivityManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.chibi.waie.R

/**
 * Parent class for all the Activity, So we can create all common methods and variables which are
 * all needed for Activities
 */
open class BaseActivity : AppCompatActivity() {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
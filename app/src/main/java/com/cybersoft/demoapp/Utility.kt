package com.cybersoft.demoapp

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object Utility {

    fun isInternetConectionAvailable(context: Context): Boolean{

        val conMgr = context.getApplicationContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMgr.activeNetworkInfo

        if(netInfo == null || !netInfo.isConnected || !netInfo.isAvailable){
            Toast.makeText(context, context.getText(R.string.internet_not_available), Toast.LENGTH_SHORT).show()
            return false
        }
        return true

    }

    fun stopRefreshing(swipeRefreshLayout: SwipeRefreshLayout){
        if (swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
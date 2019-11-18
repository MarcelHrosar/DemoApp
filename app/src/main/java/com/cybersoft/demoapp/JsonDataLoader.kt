package com.cybersoft.demoapp

import android.util.Log
import com.cybersoft.demoapp.dagger.DaggerDaggerComponent
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class JsonDataLoader @Inject constructor(var url: String, val responseHandler: ResponseHandler) {

    val TAG = this.javaClass.simpleName

    fun loadData(){
        val dependencyModule = DaggerDaggerComponent.create()
        val client = dependencyModule.getOkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        try{
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    responseHandler.onFailure()
                }

                override fun onResponse(call: Call, response: Response) {
                    val resp = response.body?.string()

                    if (response.isSuccessful && resp != null){
                       responseHandler.onSuccesful(resp)
                    }
                }
            })
        }catch (e: Exception){
            Log.e(TAG, e.toString())
        }
    }

}
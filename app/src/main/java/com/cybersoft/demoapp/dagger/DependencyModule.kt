package com.cybersoft.demoapp.dagger

import android.os.Bundle
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class DependencyModule {

    @Provides
    fun getBundle(): Bundle{
        return Bundle()
    }

    @Provides
    fun getOkHttpClient(): OkHttpClient{
        return OkHttpClient()
    }
}
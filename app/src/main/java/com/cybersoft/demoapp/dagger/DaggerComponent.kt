package com.cybersoft.demoapp.dagger

import android.os.Bundle
import com.cybersoft.demoapp.UserDetailFragment
import dagger.Component
import okhttp3.OkHttpClient

@Component(modules = [DependencyModule::class])
interface DaggerComponent {

    fun getUserDetailFragment(): UserDetailFragment
    fun getBundle(): Bundle
    fun getOkHttpClient(): OkHttpClient
}
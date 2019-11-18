package com.cybersoft.demoapp

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cybersoft.demoapp.Constants.LOAD_SINGLE_USER
import com.cybersoft.demoapp.Constants.LOAD_USERS_URL
import com.google.gson.reflect.TypeToken
import com.google.gson.GsonBuilder


class MainActivityViewModel: ViewModel() {

    val TAG = this.javaClass.simpleName

    val usersList: MutableLiveData<ArrayList<User>> by lazy {
        MutableLiveData<ArrayList<User>>()
    }
    val singleUser: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    init {

        loadAllUsers()
    }

    fun loadAllUsers(){

        val jsonDataLoader = JsonDataLoader(LOAD_USERS_URL, object: ResponseHandler{
            override fun onSuccesful(json: String) {
                setDataOnMainThread(parseUsers(json, ApiUsersResponse::class.java))
            }

            override fun onFailure() {
                Log.e(TAG, "Errr: ")
            }
        })
        jsonDataLoader.loadData()

    }

    fun loadSingleUser(userId: Int){
        val jsonDataLoader = JsonDataLoader(LOAD_SINGLE_USER+userId.toString(), object: ResponseHandler{
            override fun onSuccesful(json: String) {
                setDataOnMainThread2(parseUsers(json, ApiSingleUserData::class.java))
            }

            override fun onFailure() {
                Log.e(TAG, "Errr: ")
            }
        })
        jsonDataLoader.loadData()
    }

    private fun <T,E> parseUsers(response: String, dataClass: Class<T>): E{

        val gson = GsonBuilder()

        when(dataClass.simpleName){
            ApiUsersResponse::class.java.simpleName->{
                val collectionType = object: TypeToken<ApiUsersResponse>(){}.type
                val userResp = gson.create().fromJson<ApiUsersResponse>(response, collectionType)
                return userResp.data as E
            }
            ApiSingleUserData::class.java.simpleName->{
                val collectionType = object: TypeToken<ApiSingleUserData>(){}.type
                val userResp = gson.create().fromJson<ApiSingleUserData>(response, collectionType)
                return userResp.data as E
            }
        }

        return ArrayList<User>() as E
    }

    private fun setDataOnMainThread(users: ArrayList<User>){
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            usersList.value = users
        }
    }

    private fun setDataOnMainThread2(user: User){
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            singleUser.value = user
        }
    }
}
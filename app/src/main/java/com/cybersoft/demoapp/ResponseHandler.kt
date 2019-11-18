package com.cybersoft.demoapp

interface ResponseHandler {
    fun onSuccesful(json: String)
    fun onFailure()
}
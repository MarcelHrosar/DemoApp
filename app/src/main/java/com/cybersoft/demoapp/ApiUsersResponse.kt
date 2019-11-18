package com.cybersoft.demoapp

import com.google.gson.annotations.SerializedName

data class ApiUsersResponse (
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: ArrayList<User>
)
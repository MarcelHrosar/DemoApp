package com.cybersoft.demoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.swipe_refresh_layout2

class MainActivity : AppCompatActivity() {

    val TAG = this.javaClass.simpleName

    private lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val adaper = UseresRecyclerViewAdapter(ArrayList(), this@MainActivity)
        recycler_view_users_list.layoutManager =  LinearLayoutManager(this@MainActivity)
        recycler_view_users_list.adapter = adaper

        Utility.isInternetConectionAvailable(this)

        model.usersList.observe(this, Observer<ArrayList<User>>{ userList ->
            adaper.setData(userList)
            Utility.stopRefreshing(swipe_refresh_layout2)
        })

        swipe_refresh_layout2.setOnRefreshListener {
            if (Utility.isInternetConectionAvailable(this)) {
                model.loadAllUsers()
            }else{
                Utility.stopRefreshing(swipe_refresh_layout2)
            }
        }

    }

    fun showUserDetail(userId: Int){
        val fragment = UserDetailFragment.newInstance(userId)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(
                R.id.main_layout, fragment
            ).addToBackStack(null)
            .commit()
    }
}

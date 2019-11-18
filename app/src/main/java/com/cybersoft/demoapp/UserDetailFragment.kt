package com.cybersoft.demoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cybersoft.demoapp.Constants.USER_ID_BUNDLE
import com.cybersoft.demoapp.dagger.DaggerDaggerComponent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.fragment_user_detail.swipe_refresh_layout2
import javax.inject.Inject

class UserDetailFragment @Inject constructor(): Fragment() {

    var userId: Int? = null
    lateinit var model: MainActivityViewModel

    companion object {
        fun newInstance(userId: Int): UserDetailFragment{

            val fragmentComponent = DaggerDaggerComponent.create()
            val fragment = fragmentComponent.getUserDetailFragment()

            val args = fragmentComponent.getBundle()
            args.putInt(USER_ID_BUNDLE, userId)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = arguments!!.getInt(USER_ID_BUNDLE)
        model = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        if (userId != null){
            model.loadSingleUser(userId!!)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.singleUser.observe(this, Observer<User>{user ->
            if (isAdded){
                textView_user_first_name.text = user.firstName
                textView_user_last_name.text = user.lastName
                textView_user_email.text = user.email

                Picasso.get().load(user.avatar).into(imageView_user)

                Utility.stopRefreshing(swipe_refresh_layout2)
            }
        })

        Utility.isInternetConectionAvailable(context!!)

        imageView_back_arrow.setOnClickListener {
            fragmentManager!!.popBackStack()
        }

        swipe_refresh_layout2.setOnRefreshListener {
            if (userId != null) {
                if (Utility.isInternetConectionAvailable(context!!)) {
                    model.loadSingleUser(userId!!)
                }else{
                    Utility.stopRefreshing(swipe_refresh_layout2)
                }
            }
        }

    }


}
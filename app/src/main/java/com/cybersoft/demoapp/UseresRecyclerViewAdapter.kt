package com.cybersoft.demoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UseresRecyclerViewAdapter(val users: ArrayList<User>, val context: Context): RecyclerView.Adapter<UseresRecyclerViewAdapter.UserViewHolder>() {

    val TAG = this.javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, null)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = users.get(position)

        holder.firstName.text = currentUser.firstName
        holder.lastName.text = currentUser.lastName
        holder.email.text = currentUser.email
        Picasso.get().load(currentUser.avatar).into(holder.image)

        holder.itemView.setOnClickListener {
            (context as MainActivity).showUserDetail(currentUser.id)
        }

    }

    fun setData(newUsers: ArrayList<User>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(row: View): RecyclerView.ViewHolder(row){

        var firstName = row.textView_user_first_name
        var lastName = row.textView_user_last_name
        var email = row.textView_user_email
        var image = row.imageView_user

    }
}
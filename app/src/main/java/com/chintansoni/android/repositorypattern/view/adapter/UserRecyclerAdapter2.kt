package com.chintansoni.android.repositorypattern.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.chintansoni.android.repositorypattern.R
import com.chintansoni.android.repositorypattern.databinding.ItemUserBinding
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.util.UserDiffUtilCallback
import com.chintansoni.android.repositorypattern.view.viewholder.UserViewHolder

class UserRecyclerAdapter2(context: Context) : PagedListAdapter<User, UserViewHolder>(UserDiffUtilCallback()) {

//    private var listener: ItemTouchListener
//
//    init {
//        listener = context as ItemTouchListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mUserBinding: ItemUserBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.list_item_user, parent, false)
        return UserViewHolder(mUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(holder.adapterPosition)?.let { user ->
            val clickListener: View.OnClickListener = View.OnClickListener {
                //                listener.onItemClick(user)
            }
            holder.setUser(user)
            holder.setClickListener(clickListener)
        }
    }

    interface ItemTouchListener {
        fun onItemClick(user: User)
    }
}
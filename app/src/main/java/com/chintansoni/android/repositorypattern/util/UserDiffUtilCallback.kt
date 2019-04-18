package com.chintansoni.android.repositorypattern.util

import androidx.recyclerview.widget.DiffUtil
import com.chintansoni.android.repositorypattern.model.local.entity.User

class UserDiffUtilCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}

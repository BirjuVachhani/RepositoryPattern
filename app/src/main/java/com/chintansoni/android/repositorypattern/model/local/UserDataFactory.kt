package com.chintansoni.android.repositorypattern.model.local

import androidx.paging.DataSource
import com.chintansoni.android.repositorypattern.model.local.entity.User

/*
 * Created by Birju Vachhani on 18 April 2019
 * Copyright © 2019 RepositoryPattern. All rights reserved.
 */

class UserDataFactory : DataSource.Factory<Int, User>() {

    val userDataSource = UserDataSource()

    override fun create(): DataSource<Int, User> {
        return userDataSource
    }
}
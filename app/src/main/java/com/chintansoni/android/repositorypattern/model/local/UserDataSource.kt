package com.chintansoni.android.repositorypattern.model.local

import androidx.paging.PageKeyedDataSource
import com.chintansoni.android.repositorypattern.model.local.dao.UserDao
import com.chintansoni.android.repositorypattern.model.local.entity.User
import com.chintansoni.android.repositorypattern.model.remote.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber


/*
 * Created by Birju Vachhani on 18 April 2019
 * Copyright © 2019 RepositoryPattern. All rights reserved.
 */

class UserDataSource : PageKeyedDataSource<Int, User>(), KoinComponent {

    private val apiService: ApiService by inject()
    private val userDao: UserDao by inject()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        GlobalScope.launch {
            try {
                Timber.e("Initial data fetching for page 1")
                val response = apiService.getUsers(1).await()
                response.results?.let {
                    val list = it.map { result ->
                        User(id = 0,
                                name = result.name,
                                email = result.email,
                                gender = result.gender,
                                cell = result.cell,
                                picture = result.picture,
                                location = result.location,
                                dob = result.dob)
                    }
                    callback.onResult(list, null, 2)
                    Timber.e("Initial Fetched Data: ${list.size}")
                } ?: Timber.e("Null Initial Data")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        GlobalScope.launch {
            try {
                Timber.e("Fetching data for: page ${params.key}")
                val response = apiService.getUsers(params.key).await()
                response.results?.let {
                    val nextKey = params.key + 1
                    val list = it.map { result ->
                        User(id = 0,
                                name = result.name,
                                email = result.email,
                                gender = result.gender,
                                cell = result.cell,
                                picture = result.picture,
                                location = result.location,
                                dob = result.dob)
                    }
                    callback.onResult(list, nextKey)
                    Timber.e("Fetched Data: ${list.size} for Page: ${params.key}")
                } ?: Timber.e("Null Data for page: ${params.key}")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {

    }
}
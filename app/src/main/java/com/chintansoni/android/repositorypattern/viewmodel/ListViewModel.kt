package com.chintansoni.android.repositorypattern.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.chintansoni.android.repositorypattern.model.Resource
import com.chintansoni.android.repositorypattern.model.UserRepository
import com.chintansoni.android.repositorypattern.model.local.UserDataFactory
import com.chintansoni.android.repositorypattern.model.local.entity.User
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


class ListViewModel constructor(private var userRepository: UserRepository) : ViewModel() {

    val userLiveData = MutableLiveData<Resource<List<User>>>()
    var userPagedLiveData: LiveData<PagedList<User>>
    val feedDataFactory = UserDataFactory()
    val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(15)
            .setPrefetchDistance(15)
            .setPageSize(20).build()
    private val observer = Observer<Resource<List<User>>> {
        userLiveData.postValue(it)
    }

    fun refresh() {
        userPagedLiveData.value!!.dataSource.invalidate()
    }

    init {
//        userRepository.userLiveData.observeForever(observer)

        val executor = Executors.newFixedThreadPool(5)


//        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData()
//        ) { dataSource -> dataSource.getNetworkState() }


        userPagedLiveData = LivePagedListBuilder(feedDataFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()
    }

    fun refreshUsers() {
        viewModelScope.launch {
            userRepository.refresh()
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.fetchUsers(true)
        }
    }

    fun getNextPageUsers() {
        viewModelScope.launch {
            userRepository.getNextPageUsers()
        }
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.userLiveData.removeObserver(observer)
    }
}

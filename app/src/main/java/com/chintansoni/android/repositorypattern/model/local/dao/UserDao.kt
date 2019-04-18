package com.chintansoni.android.repositorypattern.model.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.*
import com.chintansoni.android.repositorypattern.model.local.DatabaseConstants
import com.chintansoni.android.repositorypattern.model.local.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM " + DatabaseConstants.mTableUser)
    suspend fun getAll(): LiveData<PagedList<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userList: List<User>)

    @Query("DELETE FROM " + DatabaseConstants.mTableUser)
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAllUsers(userList: List<User>) {
        insert(userList)
    }
}
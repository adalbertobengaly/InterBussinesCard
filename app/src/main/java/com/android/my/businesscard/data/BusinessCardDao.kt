package com.android.my.businesscard.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BusinessCardDao {
    @Query("SELECT * FROM BusinessCard")
    fun getAll() : LiveData<List<BusinessCard>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)

    @Delete
    fun delete(businessCard: BusinessCard)

    @Update
    fun update(businessCard: BusinessCard)

}
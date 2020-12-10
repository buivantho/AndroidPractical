package com.trinhngovan.androidpractical.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.trinhngovan.androidpractical.entity.Feedback
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedbackDao {
    @Insert
    suspend fun insert(feedback: Feedback)

    @Query("SELECT COUNT('id') FROM feedback")
    fun countRecord(): Flow<Int>
}
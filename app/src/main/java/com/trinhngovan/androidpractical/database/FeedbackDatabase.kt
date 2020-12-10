package com.trinhngovan.androidpractical.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.trinhngovan.androidpractical.entity.Feedback

@Database(entities = [Feedback::class], version = 1, exportSchema = false)
abstract class FeedbackDatabase : RoomDatabase() {
    abstract fun feedbackDao(): FeedbackDao

    companion object {
        private var database: FeedbackDatabase? = null
        fun getInstance(context: Context): FeedbackDatabase {
            synchronized(this) {
                if (database == null) {
                    database =
                        Room.databaseBuilder(context, FeedbackDatabase::class.java, "feedback_db")
                            .build()
                }
                return database as FeedbackDatabase
            }
        }
    }
}
package com.trinhngovan.androidpractical.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback")
class Feedback(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var name: String,
    var email: String,
    var feature: String,
    var message: String,
    var isResponse: Boolean
)
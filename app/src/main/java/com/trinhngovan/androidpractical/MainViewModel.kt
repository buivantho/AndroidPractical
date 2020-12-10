package com.trinhngovan.androidpractical

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.trinhngovan.androidpractical.database.FeedbackDatabase
import com.trinhngovan.androidpractical.entity.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context) : ViewModel() {
    private val database: FeedbackDatabase = FeedbackDatabase.getInstance(context)
    val feedbackCount = database.feedbackDao().countRecord().flowOn(Dispatchers.IO).asLiveData()

    fun saveFeedback(feedback: Feedback) {
        viewModelScope.launch(Dispatchers.IO) {
            database.feedbackDao().insert(feedback)
        }
    }
}
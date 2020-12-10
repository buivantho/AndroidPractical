package com.trinhngovan.androidpractical

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.trinhngovan.androidpractical.databinding.ActivityMainBinding
import com.trinhngovan.androidpractical.entity.Feedback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter
            .createFromResource(this,
                R.array.feature_array, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_item)
                binding.userFeedbackFeatureInput.adapter = it
            }

        binding.userNameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrBlank()) {
                    binding.userNameInput.error = "This field cannot be blank"
                } else {
                    binding.userNameInput.error = null
                }
            }
        })

        binding.userEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(p0).matches()) {
                    binding.userEmailInput.error = "Must be email address"
                } else {
                    binding.userEmailInput.error = null
                }
            }
        })

        binding.userMessageInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrBlank()) {
                    binding.userMessageInput.error = "This field cannot be blank"
                } else {
                    binding.userMessageInput.error = null
                }
            }
        })

        binding.sendBtn.setOnClickListener {
            if (!isInputError() && !isInputEmpty()) {
                binding.apply {
                    val name = userNameInput.text.toString()
                    val email = userEmailInput.text.toString()
                    val feature = userFeedbackFeatureInput.selectedItem.toString()
                    val message = userMessageInput.text.toString()
                    val isResponse = userEmailResponseInput.isChecked
                    mainViewModel.saveFeedback(
                        Feedback(
                            name = name,
                            email = email,
                            feature = feature,
                            message = message,
                            isResponse = isResponse
                        )
                    )
                }
            }
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        mainViewModel.feedbackCount.observe(this) {
            Toast.makeText(this, "$it records saved in database", Toast.LENGTH_LONG).show()
            binding.apply {
                userNameInput.setText("")
                userEmailInput.setText("")
                userMessageInput.setText("")
            }
        }
    }

    private fun isInputError(): Boolean {
        var isError = true
        binding.apply {
            if (userNameInput.error.isNullOrBlank()
                && userEmailInput.error.isNullOrBlank()
                && userMessageInput.error.isNullOrBlank()
            ) {
                isError = false
            }
        }
        return isError
    }

    private fun isInputEmpty(): Boolean {
        var isEmpty = false
        binding.apply {
            if (userNameInput.text.isNullOrBlank()
                || userEmailInput.text.isNullOrBlank()
                || userMessageInput.text.isNullOrBlank()
            ) {
                isEmpty = true
            }
        }
        return isEmpty
    }
}
package com.bangkit.firewatch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Turn On to receive notification\nFire Detector"
    }
    val text: LiveData<String> = _text
}
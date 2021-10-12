package com.android.my.businesscard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.my.businesscard.data.BusinessCardRepository

class MainViewModelFactory(private val repository: BusinessCardRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknow ViewModel class")
        }
    }
}
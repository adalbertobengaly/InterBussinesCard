package com.android.my.businesscard.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.my.businesscard.data.BusinessCard
import com.android.my.businesscard.data.BusinessCardRepository

class MainViewModel(private val businessCardRepository: BusinessCardRepository): ViewModel() {

    fun insert(businessCard: BusinessCard) {
        businessCardRepository.insert(businessCard)
    }

    fun delete(businessCard: BusinessCard) {
        businessCardRepository.delete(businessCard)
    }

    fun update(businessCard: BusinessCard) {
        businessCardRepository.update(businessCard)
    }

    fun getAll(): LiveData<List<BusinessCard>> {
        return businessCardRepository.getAll()
    }
}

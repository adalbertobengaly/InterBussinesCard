package com.android.my.businesscard

import android.app.Application
import com.android.my.businesscard.data.AppDatabase
import com.android.my.businesscard.data.BusinessCardRepository

class App : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessCardDao()) }
}
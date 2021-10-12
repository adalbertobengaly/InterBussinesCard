package com.android.my.businesscard.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class BusinessCard(
    @PrimaryKey(autoGenerate = true) val id :Int = 0,
    val nome: String,
    val email: String,
    val telefone: String,
    val empresa: String,
    val fundoPersonalizado: String
): Serializable

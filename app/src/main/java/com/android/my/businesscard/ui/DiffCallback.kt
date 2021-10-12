package com.android.my.businesscard.ui

import androidx.recyclerview.widget.DiffUtil
import com.android.my.businesscard.data.BusinessCard

class DiffCallback: DiffUtil.ItemCallback<BusinessCard>() {
    override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem == newItem
    override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard) = oldItem.id == newItem.id
}

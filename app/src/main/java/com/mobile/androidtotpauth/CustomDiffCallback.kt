package com.mobile.androidtotpauth

import android.support.v7.util.DiffUtil



/**
 * Created by Kartik Kumar Gujarati on 2/20/18 2:52 PM
 * Copyright (c) 2018. All rights reserved.
 */

class CustomDiffCallback(private val oldList: List<AuthAccount>,
                         private val newList: List<AuthAccount>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].path == newList[newItemPosition].path
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].path == newList[newItemPosition].path
    }

}
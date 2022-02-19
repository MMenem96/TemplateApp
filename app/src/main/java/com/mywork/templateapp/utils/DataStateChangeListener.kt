package com.mywork.templateapp.utils

import com.mywork.templateapp.utils.network.DataState

interface DataStateChangeListener {

    fun onDataStateChange(dataState: DataState<*>?)
//    fun expandAppBar()
    fun hideSoftKeyboard()
//    fun isStoragePermissionGranted(): Boolean
}
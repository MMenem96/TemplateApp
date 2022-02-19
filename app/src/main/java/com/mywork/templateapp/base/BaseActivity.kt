package com.mywork.templateapp.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mywork.templateapp.session.SessionManager
import com.mywork.templateapp.utils.*
import com.mywork.templateapp.utils.network.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), DataStateChangeListener,
    UICommunicationListener {
    private val TAG = "BaseActivity"

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUserSettings()
    }

    private fun initUserSettings() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        MainUtils.initializeSelectedLanguage(
            this,
            sharedPreferences.getString(PreferenceKeys.USER_LANGUAGE, "en")
        )
    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        Log.d(TAG, "onDataStateChange: ")
        dataState?.let {
            GlobalScope.launch(Dispatchers.Main) {
                showProgressBar(it.loading.isLoading)
                it.error?.let { errorEvent ->
                    Log.d(TAG, "onDataStateChange: Error")
                    handleStateErrorEvent(errorEvent)
                }
                it.data?.let {
                    it.response?.let { responseEvent ->
                        Log.d(TAG, "onDataStateChange: Success")
                        handleStateResponse(responseEvent)
                    }
                }
            }
        }
    }

    private fun handleStateResponse(event: Event<Response>) {
        event.getContentIfNotHandled()?.let {
            when (it.responseType) {
                is ResponseType.Dialog -> {
                    it.message?.let { message -> displaySuccessDialog(message) }
                    it.localizedMessage?.let { message -> displaySuccessDialog(message) }
                }
                is ResponseType.Toast -> {
                    it.message?.let { message -> displayToast(message) }
                }
                is ResponseType.None -> {
                    Log.d(TAG, "handleStateErrorEvent: ${it.message}")
                }
            }

        }
    }

    private fun handleStateErrorEvent(errorEvent: Event<StateError>) {
        errorEvent.getContentIfNotHandled()?.let {
            when (it.response.responseType) {
                is ResponseType.Dialog -> {
                    it.response.message?.let { message -> displayErrorDialog(message) }
                    it.response.localizedMessage?.let { message -> displayErrorDialog(message) }
                }
                is ResponseType.Toast -> {
                    it.response.message?.let { message -> displayToast(message) }
                }
                is ResponseType.None -> {
                    Log.e(TAG, "handleStateErrorEvent: ${it.response.message}")
                }


            }

        }
    }

    abstract fun showProgressBar(show: Boolean)

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun onUIListOptionsReceived(uiListMultiSelection: UIListMultiSelection) {
        when (uiListMultiSelection.uiMessageType) {
            is UIMessageType.MultiChoiceDialog -> {
                multipleChoiceDialog(
                    uiListMultiSelection.title,
                    uiListMultiSelection.list,
                    uiListMultiSelection.indices,
                    uiListMultiSelection.uiMessageType.callback
                )
            }
        }
    }

    override fun onUIListOptionResReceived(uiListSingleResSelection: UIListSingleResSelection) {
        when (uiListSingleResSelection.uiMessageType) {
            is UIMessageType.SingleChoiceDialog -> {
                singleChoiceResDialog(
                    uiListSingleResSelection.title,
                    uiListSingleResSelection.list,
                    uiListSingleResSelection.pos,
                    uiListSingleResSelection.uiMessageType.callback
                )
            }
        }
    }

    override fun onUIListOptionStringReceived(uiListSingleStringSelection: UIListSingleStringSelection) {
        when (uiListSingleStringSelection.uiMessageType) {
            is UIMessageType.SingleChoiceDialog -> {
                singleChoiceStringDialog(
                    uiListSingleStringSelection.title,
                    uiListSingleStringSelection.list,
                    uiListSingleStringSelection.pos,
                    uiListSingleStringSelection.uiMessageType.callback
                )
            }
        }
    }

    override fun onUIListOptionObjectReceived(uiListSingleObjectSelection: UIListSingleObjectSelection) {
        singleChoiceObjectDialog(
            uiListSingleObjectSelection.title,
            uiListSingleObjectSelection.adapter,
            uiListSingleObjectSelection.callback
        )
    }

    override fun onUIDateTimeReceived(uiDateTimePicker: UIDateTimePicker) {
        when (uiDateTimePicker.uiMessageType) {
            is UIMessageType.DateTimePickerDialog -> {
                dateTimePickerDialog(
                    uiDateTimePicker.needTime,
                    uiDateTimePicker.currentDateTime,
                    uiDateTimePicker.uiMessageType.callback
                )
            }
        }
    }

    override fun onUIMessageReceived(uiMessage: UIMessage) {
        when (uiMessage.uiMessageType) {
            is UIMessageType.AreYouSureDialog -> {
                areYouSureDialog(uiMessage.message, uiMessage.uiMessageType.callback)
            }

            is UIMessageType.Toast -> {
                displayToast(uiMessage.message)
            }
            is UIMessageType.Dialog -> {
                displayInfoDialog(uiMessage.message)
            }
            is UIMessageType.None -> {
                Log.d(TAG, "onUIMessageReceived: ${uiMessage.message}")
            }
        }
    }
}
package com.mywork.templateapp.utils

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.list.customListAdapter
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.mywork.templateapp.R
import java.util.*

//Extension functions


fun Activity.displayToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.displaySuccessDialog(message: String?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_success)
            message(text = message)
            positiveButton(R.string.text_ok)
        }
}

fun Activity.displaySuccessDialog(message: Int?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_success)
            message(res = message)
            positiveButton(R.string.text_ok)
        }
}

fun Activity.displayErrorDialog(message: String?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(text = message)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.displayErrorDialog(message: Int?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_error)
            message(res = message)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.displayInfoDialog(message: String?) {
    MaterialDialog(this)
        .show {
            title(R.string.text_info)
            message(text = message)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.areYouSureDialog(message: String?, callback: AreYouSureCallback) {
    MaterialDialog(this)
        .show {
            title(R.string.are_you_sure)
            message(text = message)
            cornerRadius(12f)
            positiveButton(R.string.text_yes) {
                callback.proceed()
            }
            negativeButton(R.string.text_cancel) {
                callback.cancel()
            }
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.areYouSureDialogWithCustomLayout(
    title: String?,
    message: String?,
    view: View?,
    callback: AreYouSureCallback
) {
    MaterialDialog(this)
        .show {
            title(text = title)
//            message(text = message)
            cornerRadius(12f)
            positiveButton(R.string.submit) {
                callback.proceed()
            }
            negativeButton(R.string.text_cancel) {
                callback.cancel()
            }
            icon(R.mipmap.ic_launcher)
            customView(view = view)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.singleChoiceResDialog(
    title: String?,
    list: Int,
    pos: Int,
    callback: SingleChoiceCallback
) {
//    var selectedIndex: Int = 0
    MaterialDialog(this)
        .show {
            listItemsSingleChoice(
                res = list,
                checkedColor = resources.getColor(R.color.purple_700),
                initialSelection = pos
            ) { dialog, index, text ->
                // Invoked when the user selects an item
//                selectedIndex = index
                callback.proceed(index)
            }
            title(text = title)
            cornerRadius(12f)
            positiveButton(R.string.text_yes)
//            {
//                callback.proceed(selectedIndex)
//            }
            negativeButton(R.string.text_cancel)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.singleChoiceStringDialog(
    title: String?,
    list: List<CharSequence>,
    pos: Int,
    callback: SingleChoiceCallback
) {
//    var selectedIndex: Int = 0
    MaterialDialog(this)
        .show {
            listItemsSingleChoice(
                items = list,
                checkedColor = resources.getColor(R.color.purple_700),
                initialSelection = pos
            ) { dialog, index, text ->
                // Invoked when the user selects an item
//                selectedIndex = index
                callback.proceed(index)
            }
            title(text = title)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
//            {
//                callback.proceed(selectedIndex)
//            }
            negativeButton(R.string.text_cancel)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.singleChoiceObjectDialog(
    title: String?,
    adapter: RecyclerView.Adapter<*>,
    callback: AreYouSureCallback
) {
    MaterialDialog(this)
        .show {
            title(text = title)
            cornerRadius(12f)
            positiveButton(R.string.text_ok) {
                callback.proceed()
            }
            negativeButton(R.string.text_cancel) {
                callback.cancel()
            }
            customListAdapter(adapter)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}


fun Activity.multipleChoiceDialog(
    title: String?,
    items: List<CharSequence>,
    initialSelection: IntArray,
    callback: MultiChoiceCallback
) {
//    var selectedIndex: Int = 0
    MaterialDialog(this)
        .show {
            listItemsMultiChoice(
                items = items,
                initialSelection = initialSelection
            ) { dialog, indices, items ->
                callback.proceed(indices, items)

            }


            title(text = title)
            cornerRadius(12f)
            positiveButton(R.string.text_ok)
//            {
//                callback.proceed(selectedIndex)
//            }
            negativeButton(R.string.text_cancel)
            icon(R.mipmap.ic_launcher)
//            setTheme(R.style.AppTheme)
        }
}

fun Activity.dateTimePickerDialog(
    needTime: Boolean,
    currentDateTime: Calendar?,
    callback: DateCallback
) {
    MaterialDialog(this)
        .show {
            if (needTime)
                dateTimePicker(
                    minDateTime = currentDateTime,
                    currentDateTime = currentDateTime
                ) { dialog, datetime ->
                    callback.proceed(datetime)

                } else
                datePicker(
                    minDate = currentDateTime,
                    currentDate = currentDateTime
                ) { dialog, date ->
                    callback.proceed(date)
                }
            cornerRadius(12f)
            positiveButton(R.string.text_yes)
            negativeButton(R.string.text_cancel)
//            setTheme(R.style.AppTheme)
        }
}

interface AreYouSureCallback {
    fun proceed()
    fun cancel()
}

interface SingleChoiceCallback {
    fun proceed(pos: Int)
}

interface MultiChoiceCallback {
    fun proceed(indices: IntArray, items: List<CharSequence>)
}

interface DateCallback {
    fun proceed(dateTime: Calendar?)
}

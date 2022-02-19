package com.mywork.templateapp.session

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mywork.templateapp.data.models.AuthUser
import com.mywork.templateapp.presistence.AuthTokenDao
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionManager
@Inject constructor(val context: Context, val authTokenDao: AuthTokenDao) {
    private val TAG = "SessionManager"

    private val _cashedToken = MutableLiveData<AuthUser?>()

    val cachedToken: LiveData<AuthUser?>
        get() = _cashedToken

    fun login(newValue: AuthUser) {
        setValue(newValue)
    }

    fun logout(newValue: AuthUser? = null) {
        GlobalScope.launch(IO) {
            var errorMessage: String? = null
            try {
                cachedToken.value!!.id?.let {
                    authTokenDao.removeToken(it)
                }
            } catch (e: CancellationException) {
                Log.d(TAG, "logout: ${e.message}")
                errorMessage = e.message
            } catch (e: Exception) {
                Log.d(TAG, "logout: ${e.message}")
                errorMessage = e.message
            } finally {
                errorMessage?.let {
                    Log.e(TAG, "logout: $errorMessage")
                }
                Log.d(TAG, "logout: finally...")
                setValue(null)
            }
        }
    }

    private fun setValue(newValue: AuthUser?) {
        GlobalScope.launch(Main) {
            if (_cashedToken.value != newValue) {
                _cashedToken.value = newValue
            }
        }
    }

    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}
package com.mywork.templateapp.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*


class MainUtils {

    companion object {
        fun initializeSelectedLanguage(context: Context, userLanguage: String?): Configuration {
            // Create a new Locale object
            val locale = Locale(userLanguage, "MA")
            Locale.setDefault(locale)
            // Create a new configuration object
            val config = Configuration()
            // Set the locale of the new configuration
            config.locale = locale
            // Update the configuration of the App context
            context.resources.updateConfiguration(
                config,
                context.resources.displayMetrics
            )
            return config
        }

        fun isNetworkError(msg: String): Boolean {
            when {
                msg.contains(Constants.UNABLE_TO_RESOLVE_HOST) -> return true
                else -> return false
            }
        }

        fun isPaginationDone(msg: String): Boolean {
            when {
                msg.contains(Constants.PAGINATION_DONE) -> return true
                else -> return false
            }
        }
    }
}
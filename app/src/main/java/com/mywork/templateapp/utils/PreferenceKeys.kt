package com.mywork.templateapp.utils

import com.mywork.templateapp.BuildConfig

class PreferenceKeys {

    companion object {

        // Shared Preference Files:
        const val APP_PREFERENCES: String = "${BuildConfig.APPLICATION_ID}.APP_PREFERENCES"

        // Shared Preference Keys here
        const val USER_LANGUAGE: String = "${BuildConfig.APPLICATION_ID}.USER_LANGUAGE"


    }
}
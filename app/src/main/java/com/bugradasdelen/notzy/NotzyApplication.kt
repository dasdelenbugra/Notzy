package com.bugradasdelen.notzy

import android.app.Application
import android.content.Context
import com.bugradasdelen.notzy.utils.locale.LocaleHelper
import com.bugradasdelen.notzy.utils.theme.ThemeHelper

class NotzyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Apply saved theme preference
        val sharedPrefs = getSharedPreferences("NotzyPrefs", Context.MODE_PRIVATE)
        val themePref =
            sharedPrefs.getString("theme", ThemeHelper.DEFAULT_MODE) ?: ThemeHelper.DEFAULT_MODE
        ThemeHelper.applyTheme(themePref)
    }

    override fun attachBaseContext(base: Context) {
        // Apply saved language preference
        val sharedPrefs = base.getSharedPreferences("NotzyPrefs", Context.MODE_PRIVATE)
        val languagePref = sharedPrefs.getString("language", "en") ?: "en"
        super.attachBaseContext(LocaleHelper.setLocale(base, languagePref))
    }
}
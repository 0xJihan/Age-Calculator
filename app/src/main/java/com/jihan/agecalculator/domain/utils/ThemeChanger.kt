package com.jihan.agecalculator.domain.utils

import android.content.Context

object ThemeChanger {

    private const val PREFS_NAME = "theme"
    private const  val PREFS_KEY = "isDark"

    fun getCurrentTheme(context: Context):Boolean{
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(PREFS_KEY, false)
    }

    fun setTheme(context: Context, isDark:Boolean){
        val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putBoolean(PREFS_KEY, isDark)
            apply()
        }
    }


}
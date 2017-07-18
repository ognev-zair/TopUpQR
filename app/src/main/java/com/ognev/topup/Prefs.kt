package com.ognev.topup

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

  init {
    editor = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
  }

  companion object {
    private val APP_PREFERENCES = "beacon_prefs"
    private val PHONE_NUMBER = "PHONE_NUMBER"

    private lateinit var editor: SharedPreferences

    fun savePhoneNumber(phoneNumber: String?) {
      editor.edit().putString(PHONE_NUMBER, phoneNumber).commit()
    }

    fun getPhoneNumber(): String? {
      return editor.getString(PHONE_NUMBER, null)
    }

  }

}

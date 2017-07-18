package com.ognev.topup

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by ognev on 7/4/17.
 */
class App : Application() {

  companion object {

  }

  override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
    Prefs(this)
  }
}
package com.ognev.topup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.enter
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.phonenumber

/**
 * A login screen that offers login via phoneNumber/password.
 */
class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    if (!TextUtils.isEmpty(Prefs.getPhoneNumber())) {
      startActivity(Intent(this@LoginActivity, MainActivity::class.java)
          .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK))
      finish()
    }

    phonenumber!!.setOnEditorActionListener(
        TextView.OnEditorActionListener { textView, id, keyEvent ->
          if (id == R.id.phonenumber || id == EditorInfo.IME_NULL) {
            attemptLogin()
            return@OnEditorActionListener true
          }
          false
        })

    enter.setOnClickListener { attemptLogin() }

  }

  private fun attemptLogin() {
    // Reset errors.
    phonenumber.error = null
    password!!.error = null

    // Store values at the time of the login attempt.
    val email = phonenumber!!.text.toString()
    val passwordS = password!!.text.toString()

    var cancel = false
    var focusView: View? = null

    // Check for a valid password, if the user entered one.
    if (!TextUtils.isEmpty(passwordS) && !isPasswordValid(passwordS)) {
      password.error = getString(R.string.error_invalid_password)
      focusView = password
      cancel = true
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
      phonenumber!!.error = getString(R.string.error_field_required)
      focusView = phonenumber
      cancel = true
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView!!.requestFocus()
    } else {
      startActivity(Intent(this@LoginActivity, CodeRequestActivity::class.java)
          .putExtra(C.PHONE_NUMBER,
              phonenumber!!.text.toString()))
    }
  }

  private fun isPasswordValid(password: String): Boolean {
    //TODO: Replace this with your own logic
    return password.length > 4
  }


}


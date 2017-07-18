package com.ognev.topup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_code_request.back
import kotlinx.android.synthetic.main.activity_code_request.code
import kotlinx.android.synthetic.main.activity_code_request.enter
import kotlinx.android.synthetic.main.activity_code_request.returnBack
import java.util.concurrent.TimeUnit

/**
 * Created by ognev on 7/4/17.
 */
class CodeRequestActivity : AppCompatActivity() {
  private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

  private var phoneNumber: String? = null;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_code_request)


    back.setOnClickListener { finish() }
    phoneNumber = intent.getStringExtra(C.PHONE_NUMBER);

    enter!!.setOnClickListener {
      startActivity(Intent(this@CodeRequestActivity, MainActivity::class.java))
    }

    returnBack!!.setOnClickListener {
      finish()
    }

    mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

      override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        // This callback will be invoked in two situations:
        // 1 - Instant verification. In some cases the phone number can be instantly
        //     verified without needing to send or enter a verification code.
        // 2 - Auto-retrieval. On some devices Google Play services can automatically
        //     detect the incoming verification SMS and perform verificaiton without
        //     user action.
//                Log.d(FragmentActivity.TAG, "onVerificationCompleted:" + credential)

//                signInWithPhoneAuthCredential(credential)
        Prefs.savePhoneNumber(phoneNumber)
        code.setText(credential.smsCode)

        startActivity(Intent(this@CodeRequestActivity, MainActivity::class.java)
            .putExtra(C.PHONE_NUMBER,
                phoneNumber).setFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK))

      }

      override fun onVerificationFailed(e: FirebaseException) {
        // This callback is invoked in an invalid request for verification is made,
        // for instance if the the phone number format is not valid.
//                Log.w(FragmentActivity.TAG, "onVerificationFailed", e)

        Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT)
            .show()

        if (e is FirebaseAuthInvalidCredentialsException) {
          // Invalid request
          // ...
        } else if (e is FirebaseTooManyRequestsException) {
          // The SMS quota for the project has been exceeded
          // ...
        }

        // Show a message and update the UI
        // ...
      }

      override fun onCodeSent(verificationId: String?,
          token: PhoneAuthProvider.ForceResendingToken?) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.
//                Log.d(FragmentActivity.TAG, "onCodeSent:" + verificationId!!)

        // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId
//                mResendToken = token

        // ...
      }
    }

    PhoneAuthProvider.getInstance().verifyPhoneNumber(
        phoneNumber as String, // Phone number to verify
        60, // Timeout duration
        TimeUnit.SECONDS, // Unit of timeout
        this@CodeRequestActivity, // Activity (for callback binding)
        mCallbacks);        // OnVerificationStateChangedCallbacks

  }
}
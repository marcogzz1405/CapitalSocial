package com.example.capitalsocial.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.example.capitalsocial.R
import com.example.capitalsocial.api.ListenerRegister
import com.example.capitalsocial.api.PresenterRegister
import com.example.capitalsocial.bases.BaseActivity
import com.example.capitalsocial.models.Register
import com.example.capitalsocial.utilities.App
import com.example.capitalsocial.utilities.Settings
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), ListenerRegister {

    private var presenterRegister: PresenterRegister? = null

    var capitalApplication: App? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        capitalApplication = App()

        val typeFace = Typeface.createFromAsset(assets, "font/Roboto-Black.ttf")
        welcomeTitle.typeface = typeFace
        email_scan_button.typeface = typeFace
        email.typeface = typeFace
        password.typeface = typeFace
        email_login_button.typeface = typeFace
        email_register_button.typeface = typeFace
        reset_password.typeface = typeFace

        presenterRegister = PresenterRegister()
        presenterRegister?.listener = this@LoginActivity

        email_scan_button.setOnClickListener {
            scan()
        }

        email_login_button.setOnClickListener {
            attempLogin()
        }
    }

    private fun scan() {
        showMessage(1, "Error", "Esta en mantenimiento esta parte.")
    }

    private fun attempLogin() {
        // Reset errors.
        email.error = null
        password.error = null
        // Store values at the time of the login attemp
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address
        if(TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        }

        // Check for a valid password, if the user entered one
        if(TextUtils.isEmpty(passwordStr)){
            password.error = getString(R.string.error_field_required)
            focusView = password
            cancel = true
        }

        if(cancel){
            focusView?.requestFocus()
        } else {
            hideKeyboard()
            presenterRegister?.attempRegister(emailStr, passwordStr)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                Log.e("LoginActivity", "Resultado ---> " + data.toString())
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                Log.e("LoginActivity", "Cancel")
            }
        }
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.INVISIBLE
        login_form.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 0 else 1).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_form.visibility = if (show) View.GONE else View.VISIBLE
                }
            })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
    }

    override fun showProgress() {
        showProgress(true)
    }

    override fun hideProgress() {
        showProgress(false)
    }

    override fun showErrorMessage(message: String) {
        if(message != null){
            showMessage(Settings.TYPE_MESSAGE_ERROR, getString(R.string.error_title), message)
        }
    }

    override fun successEntry(register: Register) {
        // Save the response in SharedPreferences.
        App.prefs!!.id = register!!.id
        App.prefs!!.token = register!!.token
        email.setText("")
        password.setText("")
        launchMainActivity()
        finish()
    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}

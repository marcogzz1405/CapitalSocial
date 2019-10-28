package com.example.capitalsocial.bases

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capitalsocial.MainActivity
import com.example.capitalsocial.R
import com.example.capitalsocial.activities.LoginActivity
import com.example.capitalsocial.activities.MapActivity
import com.example.capitalsocial.utilities.Settings

abstract class BaseActivity : AppCompatActivity() {

    fun showMessage(type: Int, title: String, message: String){
        if(type == Settings.TYPE_MESSAGE_INFO || type == Settings.TYPE_MESSAGE_ERROR) {
            AlertDialog.Builder(this@BaseActivity).setTitle(title).setMessage(message).setPositiveButton(getString(R.string.accept)) { dialogInterface, _ -> dialogInterface.dismiss() }
                .create().show()
        }
    }

    fun hideKeyboard() {
        // Check if no view has focus:
        val view = currentFocus
        if(view != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun launchLoginActivity() {
        startActivity(LoginActivity.getInstance(this@BaseActivity))
    }

    fun launchMainActivity() {
        startActivity(MainActivity.getInstance(this@BaseActivity))
    }

    fun launchMapActivity() {
        startActivity(MapActivity.getInstance(this@BaseActivity))
    }

}
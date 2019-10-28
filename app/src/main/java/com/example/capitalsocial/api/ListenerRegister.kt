package com.example.capitalsocial.api

import com.example.capitalsocial.bases.BaseListener
import com.example.capitalsocial.models.Register

interface ListenerRegister : BaseListener {

    fun showErrorMessage(message: String)

    fun successEntry(register: Register)

}

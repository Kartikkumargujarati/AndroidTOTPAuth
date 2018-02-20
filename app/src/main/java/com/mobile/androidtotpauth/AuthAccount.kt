package com.mobile.androidtotpauth

import android.net.Uri

/**
 * Created by Kartik Kumar Gujarati on 2/19/18 4:49 PM
 * Copyright (c) 2018. All rights reserved.
 */

class AuthAccount(private val count: Int) {

    val uri:Uri = Uri.parse("otpauth://totp/ddddeee?secret=aaaabbbccc&issuer=Kartik")
    val scheme = uri.scheme.toLowerCase()
    val path = uri.path + count
    val authority = uri.authority
    val secret = uri.getQueryParameter("secret") + count

}
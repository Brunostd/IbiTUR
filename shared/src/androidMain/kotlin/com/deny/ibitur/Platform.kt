package com.deny.ibitur

import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"

}
package aso.mo.asoplayer.common

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import aso.mo.asoplayer.main.common.utils.Utils


@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    fun isPermissionGranted(permission: String): Boolean =
        Utils.isPermissionGranted(permission, this)
}
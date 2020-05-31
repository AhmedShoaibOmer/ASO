package aso.mo.asoplayer.main.common.view

import androidx.fragment.app.DialogFragment
import aso.mo.asoplayer.main.common.utils.Utils

open class BaseDialogFragment : DialogFragment() {
    fun isPermissionGranted(permission: String): Boolean =
        Utils.isPermissionGranted(permission, activity)
}
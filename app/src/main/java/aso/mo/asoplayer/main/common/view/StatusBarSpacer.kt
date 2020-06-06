package aso.mo.asoplayer.main.common.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View

class StatusBarSpacer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {
    private var statusHeight = 60

    init {
        if (context is Activity) {
            context.window.decorView.setOnApplyWindowInsetsListener { _, insets ->
                statusHeight = insets.systemWindowInsetTop
                requestLayout()
                insets
            }
            context.window.decorView.requestApplyInsets()
        } else {
            statusHeight = resources.getDimensionPixelSize(
                resources.getIdentifier("status_bar_height", "dimen", "android")
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, statusHeight)
    }
}
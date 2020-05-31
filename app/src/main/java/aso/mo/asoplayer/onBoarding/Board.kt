package aso.mo.asoplayer.onBoarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by Wilberforce on 28/03/2019 at 02:38.
 */
data class Board(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)
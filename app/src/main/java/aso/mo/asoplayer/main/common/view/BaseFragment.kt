package aso.mo.asoplayer.main.common.view

import androidx.fragment.app.Fragment
import aso.mo.asoplayer.R
import aso.mo.asoplayer.main.common.utils.TimeUtils
import aso.mo.asoplayer.main.common.utils.Utils
import aso.mo.asoplayer.main.songs.Song
import java.util.concurrent.TimeUnit


open class BaseFragment : Fragment() {
    fun isPermissionGranted(permission: String): Boolean =
        Utils.isPermissionGranted(permission, activity)

    fun getSongsTotalTime(songs: List<Song>): CharSequence? {
        val secs = TimeUnit.MILLISECONDS.toSeconds(TimeUtils.getTotalSongsDuration(songs))
        return getString(
            R.string.two_comma_separated_values,
            resources.getQuantityString(R.plurals.numberOfSongs, songs.size, songs.size),
            TimeUtils.formatElapsedTime(secs, activity)
        )
    }
}
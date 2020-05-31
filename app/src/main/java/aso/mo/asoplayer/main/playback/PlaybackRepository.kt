package aso.mo.asoplayer.main.playback

import android.app.Application
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import aso.mo.asoplayer.main.songs.Song

class PlaybackRepository(val application: Application) {

    // For now, we are getting all tracks in the mediastore. Later we'll use a database backed with Room to get a
    // list of last playing tracks.
    @WorkerThread
    fun loadData(): List<Song> {
        val results = mutableListOf<Song>()
        val cursor = application.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            "${MediaStore.Audio.Media.IS_MUSIC} != ?",
            arrayOf("0"),
            "${MediaStore.Audio.Media.TITLE} COLLATE NOCASE ASC"
        )
        cursor?.use {
            while (it.moveToNext()) {
                results.add(Song(it))
            }
        }
        return results
    }

}
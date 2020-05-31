package aso.mo.asoplayer.main.songs

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import aso.mo.asoplayer.main.common.data.MediaStoreRepository

open class SongsRepository(application: Application) : MediaStoreRepository<Song>(application) {

    override fun transform(cursor: Cursor): Song = Song(cursor)

    @WorkerThread
    fun fetchMatchingIds(
        uri: Uri,
        projection: Array<String>? = null,
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): List<Long> {
        val results = mutableListOf<Long>()
        val cursor = query(uri, projection, selection, selectionArgs)
        cursor?.use {
            while (it.moveToNext()) {
                results.add(it.getLong(it.getColumnIndex(MediaStore.Audio.Media._ID)))
            }
        }
        return results
    }
}
package aso.mo.asoplayer.main.albums

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.data.MediaStoreRepository
import aso.mo.asoplayer.main.common.view.BaseMediaStoreViewModel

open class AlbumsViewModel(application: Application) : BaseMediaStoreViewModel<Album>(application) {

    final override var repository: MediaStoreRepository<Album> = AlbumsRepository(application)

    override var sortOrder: String? = "${MediaStore.Audio.Albums.ALBUM} COLLATE NOCASE ASC"

    override var uri: Uri = baseAlbumUri

    final override var projection: Array<String>? = baseAlbumProjection

}

val baseAlbumProjection = arrayOf(
    MediaStore.Audio.Albums.ALBUM,
    MediaStore.Audio.Albums.ARTIST,
    MediaStore.Audio.Albums.NUMBER_OF_SONGS,
    MediaStore.Audio.Albums._ID,
    MediaStore.Audio.Albums.FIRST_YEAR,
    MediaStore.Audio.Albums.ALBUM_KEY
)

val baseAlbumUri: Uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

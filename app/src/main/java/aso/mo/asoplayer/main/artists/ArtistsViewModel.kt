package aso.mo.asoplayer.main.artists

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.data.MediaStoreRepository
import aso.mo.asoplayer.main.common.view.BaseMediaStoreViewModel

class ArtistsViewModel(application: Application) : BaseMediaStoreViewModel<Artist>(application) {
    override var repository: MediaStoreRepository<Artist> = ArtistsRepository(application)

    override var sortOrder: String? = "${MediaStore.Audio.Albums.ARTIST} COLLATE NOCASE ASC"

    override var uri: Uri = baseArtistUri

    override var projection: Array<String>? = baseArtistProjection
}


val baseArtistProjection = arrayOf(
    MediaStore.Audio.Artists.ARTIST,
    MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
    MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
    MediaStore.Audio.Artists._ID
)

val baseArtistUri: Uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
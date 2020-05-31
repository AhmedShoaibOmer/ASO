package aso.mo.asoplayer.main.artists

import android.app.Application
import android.database.Cursor
import aso.mo.asoplayer.main.common.data.MediaStoreRepository

class ArtistsRepository(application: Application) : MediaStoreRepository<Artist>(application) {
    override fun transform(cursor: Cursor): Artist = Artist(cursor)
}
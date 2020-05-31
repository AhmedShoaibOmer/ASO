package aso.mo.asoplayer.main.albums

import android.app.Application
import android.database.Cursor
import aso.mo.asoplayer.main.common.data.MediaStoreRepository

class AlbumsRepository(application: Application) : MediaStoreRepository<Album>(application) {

    override fun transform(cursor: Cursor): Album = Album(cursor)


}
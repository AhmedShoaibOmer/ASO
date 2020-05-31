package aso.mo.asoplayer.main.genres

import android.app.Application
import android.database.Cursor
import aso.mo.asoplayer.main.common.data.MediaStoreRepository

class GenresRepository(application: Application) : MediaStoreRepository<Genre>(application) {

    override fun transform(cursor: Cursor) = Genre(cursor)
}
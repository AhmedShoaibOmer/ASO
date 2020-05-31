package aso.mo.asoplayer.main.genres

import android.database.Cursor
import android.os.Parcelable
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.data.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(override val id: Long, val name: String) : Model(), Parcelable {

    constructor(cursor: Cursor) : this(
        id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Genres._ID)),
        name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Genres.NAME))
    )
}
package aso.mo.asoplayer.main.artists

import android.database.Cursor
import android.os.Parcelable
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.data.Model
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Artist(
    override val id: Long,
    val name: String,
    val songsCount: Int,
    val albumsCount: Int
) : Model(),
    Parcelable {

    constructor(data: Cursor) : this(
        name = data.getString(data.getColumnIndex(MediaStore.Audio.Artists.ARTIST)),
        songsCount = data.getInt(data.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)),
        albumsCount = data.getInt(data.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)),
        id = data.getLong(data.getColumnIndex(MediaStore.Audio.Artists._ID))
    )

}
// Copyright (c) 2019 . Wilberforce Uwadiegwu. All Rights Reserved.

package aso.mo.asoplayer.main.songs

import android.database.Cursor
import android.os.Parcelable
import android.provider.MediaStore
import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.common.data.Model
import aso.mo.asoplayer.main.common.utils.ImageUtils
import aso.mo.asoplayer.main.common.utils.Utils.getTrackNumber
import kotlinx.android.parcel.Parcelize


/**
 * Created by Wilberforce on 16/04/2019 at 01:22.
 */

@Parcelize
data class Song(
    override val id: Long,
    val title: String,
    val titleKey: String,
    val album: Album,
    val path: String,
    val duration: Long,
    val number: String,
    val artPath: String,
    var isCurrent: Boolean = false,
    var selected: Boolean = false,
    var audioId: Long? = null
) : Model(), Parcelable {
    constructor(cursor: Cursor) : this(
        id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
        titleKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE_KEY)),
        album = Album(
            cursor,
            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
        ),
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
        duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
        artPath = ImageUtils.getAlbumArtUri(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)))
            .toString(),
        number = getTrackNumber(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK)))
    )

    constructor(cursor: Cursor, audioId: Long?) : this(
        id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members._ID)),
        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE)),
        titleKey = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE_KEY)),
        album = Album(
            cursor,
            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM_ID))
        ),
        path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DATA)),
        duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.DURATION)),
        artPath = ImageUtils.getAlbumArtUri(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM_ID)))
            .toString(),
        number = getTrackNumber(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.TRACK))),
        audioId = audioId
    )
}
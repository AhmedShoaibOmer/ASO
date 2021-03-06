// Copyright (c) 2019 . Wilberforce Uwadiegwu. All Rights Reserved.

package aso.mo.asoplayer.main.albums

import android.app.Application
import android.provider.MediaStore
import aso.mo.asoplayer.main.songs.SongsViewModel
import aso.mo.asoplayer.main.songs.basicSongsSelection
import aso.mo.asoplayer.main.songs.basicSongsSelectionArg


/**
 * Created by Wilberforce on 2019-04-21 at 12:47.
 */
class AlbumSongsViewModel(application: Application) : SongsViewModel(application) {

    override var selection: String? =
        "$basicSongsSelection AND ${MediaStore.Audio.Media.ALBUM_ID} = ?"

    override var sortOrder: String? = "${MediaStore.Audio.Media.TRACK} COLLATE NOCASE ASC"

    override fun init(vararg params: Any?) {
        selectionArgs = arrayOf(basicSongsSelectionArg, params[0].toString())
        super.init()
    }
}
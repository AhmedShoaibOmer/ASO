package aso.mo.asoplayer.main.playlist

import android.app.Application
import android.provider.MediaStore
import aso.mo.asoplayer.main.common.data.MediaStoreRepository
import aso.mo.asoplayer.main.songs.Song
import aso.mo.asoplayer.main.songs.SongsViewModel
import aso.mo.asoplayer.main.songs.baseSongsProjection


class PlaylistSongsViewModel(application: Application) : SongsViewModel(application) {

    override var repository: MediaStoreRepository<Song> = PlaylistSongsRepository(application)

    override var sortOrder: String? = "${MediaStore.Audio.Media.DATE_ADDED} COLLATE NOCASE ASC"

    override var projection: Array<String>? =
        listOf(*baseSongsProjection, MediaStore.Audio.Playlists.Members.AUDIO_ID).toTypedArray()

    override fun init(vararg params: Any?) {
        uri = MediaStore.Audio.Playlists.Members.getContentUri("external", params[0] as Long)
        super.init()
    }
}
package aso.mo.asoplayer.main.playlist

import android.app.Application
import android.database.Cursor
import android.provider.MediaStore
import aso.mo.asoplayer.main.songs.Song
import aso.mo.asoplayer.main.songs.SongsRepository

class PlaylistSongsRepository(application: Application) : SongsRepository(application) {

    override fun transform(cursor: Cursor): Song {
        return Song(
            cursor,
            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists.Members.AUDIO_ID))
        )
    }
}
package aso.mo.asoplayer.main.genres

import android.app.Application
import android.provider.MediaStore
import aso.mo.asoplayer.main.songs.SongsViewModel

class GenreSongsViewModel(application: Application) : SongsViewModel(application) {

    override fun init(vararg params: Any?) {
        uri = MediaStore.Audio.Genres.Members.getContentUri("external", params[0] as Long)
        super.init()
    }
}
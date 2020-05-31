package aso.mo.asoplayer.main.artists

import android.app.Application
import android.provider.MediaStore
import aso.mo.asoplayer.main.albums.AlbumsViewModel

class ArtistAlbumsViewModel(application: Application) : AlbumsViewModel(application) {

    override fun init(vararg params: Any?) {
        uri = MediaStore.Audio.Artists.Albums.getContentUri("external", params[0] as Long)
        super.init()
    }

}
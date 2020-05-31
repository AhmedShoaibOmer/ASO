package aso.mo.asoplayer.main.common.network.image.album

import aso.mo.asoplayer.main.albums.Album
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream

class AlbumModelLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    BaseAlbumLoader<Album>(concreteLoader) {

    override fun artist(a: Album): String = a.artist

    override fun album(a: Album): String = a.name
}
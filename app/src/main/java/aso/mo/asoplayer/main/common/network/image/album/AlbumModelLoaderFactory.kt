package aso.mo.asoplayer.main.common.network.image.album

import aso.mo.asoplayer.main.albums.Album
import aso.mo.asoplayer.main.common.network.image.BaseModelLoaderFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class AlbumModelLoaderFactory : BaseModelLoaderFactory<Album>() {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Album, InputStream> {
        return AlbumModelLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java))
    }

}
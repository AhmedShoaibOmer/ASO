package aso.mo.asoplayer.main.common.network.image.artist

import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.common.network.image.BaseModelLoaderFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class ArtistModelLoaderFactory : BaseModelLoaderFactory<Artist>() {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Artist, InputStream> {
        return ArtistModelLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java))
    }

}
package aso.mo.asoplayer.main.common.network.image.album

import aso.mo.asoplayer.main.common.network.image.ImageUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream


abstract class BaseAlbumLoader<A>(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    ImageUrlLoader<A>(concreteLoader) {
    final override var lastFmUrlKey: String = "album"
    final override var spotifyUrlKey: String = "albums"

    final override fun getLastFmParams(model: A): Map<String, String>? =
        mapOf(
            Pair("method", "album.getinfo"),
            Pair("artist", artist(model)),
            Pair("album", album(model))
        )

    final override fun getSpotifyFmParams(model: A): Map<String, String>? =
        mapOf(
            Pair("q", String.format("album:%s artist:%s", album(model), artist(model))),
            Pair("type", "album")
        )

    abstract fun artist(a: A): String

    abstract fun album(a: A): String
}
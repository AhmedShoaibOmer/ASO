package aso.mo.asoplayer.main.common.network.image.artist

import aso.mo.asoplayer.main.artists.Artist
import aso.mo.asoplayer.main.common.network.image.ImageUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream

class ArtistModelLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    ImageUrlLoader<Artist>(concreteLoader) {
    override var lastFmUrlKey: String = "artist"
    override var spotifyUrlKey: String = "artists"

    override fun getLastFmParams(model: Artist): Map<String, String>? {
        return mapOf(Pair("method", "artist.getinfo"), Pair("artist", model.name))
    }

    override fun getSpotifyFmParams(model: Artist): Map<String, String>? {
        return mapOf(Pair("q", String.format("artist:%s", model.name)), Pair("type", "artist"))
    }


}
package aso.mo.asoplayer.main.common.network.image.playlist

import aso.mo.asoplayer.main.playlist.Playlist
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class PlaylistModelLoaderFactory : ModelLoaderFactory<Playlist, InputStream> {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Playlist, InputStream> {
        return PlaylistModelLoader()
    }

    override fun teardown() {}

}
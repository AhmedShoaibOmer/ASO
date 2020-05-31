package aso.mo.asoplayer.main.common.network.image.genre

import aso.mo.asoplayer.main.genres.Genre
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class GenreModelLoaderFactory : ModelLoaderFactory<Genre, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Genre, InputStream> {
        return GenreModelLoader()
    }

    override fun teardown() {}

}
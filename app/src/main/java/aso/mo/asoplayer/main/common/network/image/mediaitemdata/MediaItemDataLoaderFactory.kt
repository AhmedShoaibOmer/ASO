package aso.mo.asoplayer.main.common.network.image.mediaitemdata

import aso.mo.asoplayer.main.common.network.image.BaseModelLoaderFactory
import aso.mo.asoplayer.main.playback.MediaItemData
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class MediaItemDataLoaderFactory : BaseModelLoaderFactory<MediaItemData>() {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<MediaItemData, InputStream> {
        return MediaItemDataLoader(
            multiFactory.build(
                GlideUrl::class.java,
                InputStream::class.java
            )
        )
    }
}
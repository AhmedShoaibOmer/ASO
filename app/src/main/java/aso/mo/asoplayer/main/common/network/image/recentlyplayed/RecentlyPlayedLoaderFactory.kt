package aso.mo.asoplayer.main.common.network.image.recentlyplayed

import aso.mo.asoplayer.main.common.network.image.BaseModelLoaderFactory
import aso.mo.asoplayer.main.explore.RecentlyPlayed
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream


class RecentlyPlayedLoaderFactory : BaseModelLoaderFactory<RecentlyPlayed>() {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<RecentlyPlayed, InputStream> {
        return RecentlyPlayedLoader(
            multiFactory.build(
                GlideUrl::class.java,
                InputStream::class.java
            )
        )
    }
}
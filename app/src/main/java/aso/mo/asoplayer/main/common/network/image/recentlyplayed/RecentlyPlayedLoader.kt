package aso.mo.asoplayer.main.common.network.image.recentlyplayed

import aso.mo.asoplayer.main.common.network.image.album.BaseAlbumLoader
import aso.mo.asoplayer.main.explore.RecentlyPlayed
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import java.io.InputStream


class RecentlyPlayedLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>) :
    BaseAlbumLoader<RecentlyPlayed>(concreteLoader) {

    override fun artist(a: RecentlyPlayed): String = a.artist

    override fun album(a: RecentlyPlayed): String = a.album
}